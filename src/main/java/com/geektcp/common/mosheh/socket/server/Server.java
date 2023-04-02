package com.geektcp.common.mosheh.socket.server;

import com.geektcp.common.mosheh.socket.comparator.Writable;
import com.geektcp.common.mosheh.socket.constant.Status;
import com.geektcp.common.mosheh.socket.text.ConnectionBody;
import com.geektcp.common.mosheh.socket.text.ConnectionHeader;
import com.geektcp.common.mosheh.socket.util.StringUtils;
import com.geektcp.common.mosheh.socket.util.WritableUtils;
import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class Server {

    private volatile boolean running = true;

    private Listener listener = null;
    private Responder responder = null;
    private Worker[] workers = null;

    private BlockingQueue<Call> callQueue;

    private int numConnections = 0;

    private int port = 8090;

    private static final int readThreads = 5;
    private static final int workerThreads = 10;
    private static final int thresholdIdleConnections = 4000;
    private static final int maxConnectionsToNuke = 10;
    private static final int maxIdleTime = 2000;

    private static final int NIO_BUFFER_LIMIT = 8192;
    private static final int INITIAL_RESP_BUF_SIZE = 10240;

    private static final int maxRespSize = 1024 * 1024;
    private static final int callQueueCapacity = 4096 * 100;

    private List<Connection> connectionList = Collections.synchronizedList(new LinkedList<Connection>());

    public Server() throws IOException {
        init();
    }

    public Server(int port) throws IOException {
        this.port = port;
        init();
    }

    private void init() throws IOException {
        this.callQueue = new LinkedBlockingQueue<>(callQueueCapacity);
        this.listener = new Listener();
        this.responder = new Responder();
    }

    public static void bind(ServerSocket socket, InetSocketAddress address, int backlog) throws IOException {
        try {
            socket.bind(address, backlog);
        } catch (BindException e) {
            BindException bindException = new BindException("Binding " + address + " error: " + e.getMessage());
            bindException.initCause(e);
            throw bindException;
        } catch (SocketException e) {
            if ("Unresolved address".equals(e.getMessage())) {
                throw new UnknownHostException("Invalid hostname: " + address.getHostName());
            } else {
                Sys.p(e.getMessage());
            }
        }
    }

    private void closeConnection(Connection connection) {
        synchronized (connectionList) {
            if (connectionList.remove(connection)) {
                numConnections--;
            }
        }

        try {
            connection.close();
        } catch (IOException e) {
            Sys.p(e.getMessage());
        }
    }

    private static int channelRead(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
        return (buffer.remaining() <= NIO_BUFFER_LIMIT) ?
                channel.read(buffer) : channelIO(channel, null, buffer);
    }

    private static int channelWrite(WritableByteChannel channel, ByteBuffer buffer) throws IOException {
        return (buffer.remaining() <= NIO_BUFFER_LIMIT) ?
                channel.write(buffer) : channelIO(null, channel, buffer);
    }

    private static int channelIO(ReadableByteChannel readCh,
                                 WritableByteChannel writeCh,
                                 ByteBuffer buf) throws IOException {
        int originalLimit = buf.limit();
        int initialRemaining = buf.remaining();
        int ret = 0;

        while (buf.remaining() > 0) {
            try {
                int ioSize = Math.min(buf.remaining(), NIO_BUFFER_LIMIT);
                buf.limit(buf.position() + ioSize);

                ret = (readCh == null) ? writeCh.write(buf) : readCh.read(buf);
                if (ret < ioSize) {
                    break;
                }
            } finally {
                buf.limit(originalLimit);
            }
        }

        int nBytes = initialRemaining - buf.remaining();

        return (nBytes > 0) ? nBytes : ret;
    }

    private void setupResponse(ByteArrayOutputStream response, Call call, Status status, Writable w, String error)
            throws IOException {
        response.reset();
        DataOutputStream out = new DataOutputStream(response);

        if (status == Status.SUCCESS) {
            w.write(out);
        } else {
            WritableUtils.writeString(out, error);
        }

        call.setResponse(ByteBuffer.wrap(response.toByteArray()));
    }

    public synchronized void start() throws IOException {
        workers = new Worker[workerThreads];
        for (int i = 0; i < workerThreads; i++) {
            workers[i] = new Worker(i);
            workers[i].start();
        }

        responder.start();
        listener.start();
    }

    public synchronized void stop() {
        Sys.p("Stopping server on " + port);

        running = false;

        if (workers != null) {
            for (int i = 0; i < workerThreads; i++) {
                if (workers[i] != null) {
                    workers[i].interrupt();
                }
            }
        }

        listener.interrupt();
        listener.doStop();
        responder.interrupt();

        notifyAll();
    }

    public synchronized void join() throws InterruptedException {
        while (running) {
            wait();
        }
    }

    private class Listener extends Thread {

        private InetSocketAddress address = null;
        private ServerSocketChannel acceptChannel = null;
        private Selector acceptSelector = null;

        private int backlog = 1024;
        private int currentReader = 0;

        private Reader[] readers = null;
        private ExecutorService readPool = null;

        private long lastCleanupRunTime = 0;
        private long cleanupInterval = 10000;

        private Random rand = new Random();

        Listener() throws IOException {
            address = new InetSocketAddress(port);

            acceptChannel = ServerSocketChannel.open();
            acceptChannel.configureBlocking(false);

            bind(acceptChannel.socket(), address, backlog);

            acceptSelector = Selector.open();

            readers = new Reader[readThreads];
            readPool = new ThreadPoolExecutor(readThreads, readThreads,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1000));

            for (int i = 0; i < readThreads; i++) {
                Selector readSelector = Selector.open();
                Reader reader = new Reader(readSelector);
                readers[i] = reader;
                readPool.execute(reader);
            }

            acceptChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);

            this.setName("Listener");
            this.setDaemon(true);
        }

        public void run() {
            Sys.p(getName() + " is running on " + port);

            while (running) {
                SelectionKey acceptKey = null;

                try {
                    acceptSelector.select();

                    Iterator<SelectionKey> acceptIterator = acceptSelector.selectedKeys().iterator();
                    while (acceptIterator.hasNext()) {
                        acceptKey = acceptIterator.next();
                        acceptIterator.remove();

                        try {
                            if (acceptKey.isValid()) {
                                if (acceptKey.isAcceptable()) {
                                    doAccept(acceptKey);
                                } else if (acceptKey.isReadable()) {
                                    doRead(acceptKey);
                                }
                            }
                        } catch (IOException e) {
                            Sys.p(e.getMessage());
                        }

                        acceptKey = null;
                    }
                } catch (OutOfMemoryError e) {
                    Sys.p(getName() + " got OutOfMemoryError in Listener ", e);

                    closeCurrentConnection(acceptKey);
                    cleanupConnections(true);

                    try {
                        Thread.sleep(60000);
                    } catch (Exception ie) {
                        Sys.p(e.getMessage());
                    }
                } catch (InterruptedException e) {
                    if (running) {
                        Sys.p(getName() + " got InterruptedException in Listener "
                                + StringUtils.stringifyException(e));
                    }
                } catch (Exception e) {
                    closeCurrentConnection(acceptKey);
                }
                cleanupConnections(false);
            }

            Sys.p(getName() + " is stopping");
            synchronized (this) {
                try {
                    acceptChannel.close();
                    acceptSelector.close();
                } catch (IOException e) {
                    Sys.p(e.getMessage());
                }
                acceptChannel = null;
                acceptSelector = null;
                while (!connectionList.isEmpty()) {
                    closeConnection(connectionList.remove(0));
                }
                readPool.shutdownNow();
            }
        }

        void doAccept(SelectionKey key) throws IOException, OutOfMemoryError {
            Connection conn = null;
            ServerSocketChannel server = (ServerSocketChannel) key.channel();

            for (int i = 0; i < backlog; i++) {
                SocketChannel channel = server.accept();
                if (channel == null) {
                    return;
                }

                channel.configureBlocking(false);
                channel.socket().setTcpNoDelay(false);

                Reader reader = getReader();

                try {
                    reader.startAdd();
                    SelectionKey readKey = reader.registerChannel(channel);
                    conn = new Connection(readKey, channel, System.currentTimeMillis());
                    readKey.attach(conn);

                    synchronized (connectionList) {
                        connectionList.add(numConnections, conn);
                        numConnections++;
                    }

                    Sys.p("Got connection from " + conn.toString()
                            + ", active connections: " + numConnections
                            + ", callQueue len: " + callQueue.size());

                    if (callQueue.remainingCapacity() < 10) {
                        Sys.p("callQueue len is " + callQueue.size()
                                + ", remaining less than 10");
                    }
                } finally {
                    reader.finishAdd();
                }
            }
        }

        void doRead(SelectionKey key) throws InterruptedException {
            Connection conn = (Connection) key.attachment();
            if (conn == null) {
                return;
            }

            conn.setLastContact(System.currentTimeMillis());

            int count = 0;

            try {
                count = conn.readAndProcess();
            } catch (InterruptedException ie) {
                Sys.p(getName() + " readAndProcess got InterruptedException: ", ie);
                throw ie;
            } catch (Exception e) {
                Sys.p(getName() + " readAndProcess got Exception " + ", client: " + conn.getHostAddress()
                        + ", read bytes: " + count, e);
                count = -1;
            }

            if (count < 0) {
                Sys.p(getName() + ", the client " + conn.getHostAddress()
                        + " is closed, active connections: " + numConnections);
                closeConnection(conn);
                conn = null;
            } else {
                conn.setLastContact(System.currentTimeMillis());
            }
        }

        private void closeCurrentConnection(SelectionKey key) {
            if (key != null) {
                Connection c = (Connection) key.attachment();
                if (c != null) {
                    Sys.p(getName() + " closing client: " + c.getHostAddress()
                            + ", active connections: " + numConnections);
                    closeConnection(c);
                    c = null;
                }
            }
        }

        private void cleanupConnections(boolean force) {
            if (force || numConnections > thresholdIdleConnections) {
                long currentTime = System.currentTimeMillis();
                if (!force && (currentTime - lastCleanupRunTime) < cleanupInterval) {
                    return;
                }

                int start = 0;
                int end = numConnections - 1;

                if (!force) {
                    start = rand.nextInt() % numConnections;
                    end = rand.nextInt() % numConnections;
                    int temp;
                    if (end < start) {
                        temp = start;
                        start = end;
                        end = temp;
                    }
                }

                int i = start;
                int numNuked = 0;
                while (i <= end) {
                    Connection c;
                    synchronized (connectionList) {
                        try {
                            c = connectionList.get(i);
                        } catch (Exception e) {
                            return;
                        }
                    }

                    if (c.timedOut(currentTime)) {
                        closeConnection(c);
                        c = null;
                        numNuked++;
                        end--;

                        if (!force && numNuked == maxConnectionsToNuke) {
                            break;
                        }
                    } else {
                        i++;
                    }
                }

                lastCleanupRunTime = System.currentTimeMillis();
            }
        }

        Reader getReader() {
            currentReader = (currentReader + 1) % readers.length;
            return readers[currentReader];
        }

        synchronized void doStop() {
            if (acceptSelector != null) {
                acceptSelector.wakeup();
                Thread.yield();
            }

            if (acceptChannel != null) {
                try {
                    acceptChannel.socket().close();
                } catch (IOException e) {
                    Sys.p(getName()
                            + " got IOException while closing acceptChannel: "
                            + e);
                }
            }

            readPool.shutdownNow();
        }

        private class Reader implements Runnable {

            private Selector readSelector = null;

            private volatile boolean adding = false;

            Reader(Selector selector) {
                readSelector = selector;
            }

            public void run() {
                Sys.p("SocketReader is starting");

                synchronized (this) {
                    while (running) {
                        SelectionKey readKey;
                        try {
                            readSelector.select();
                            while (adding) {
                                this.wait(1000);
                            }
                            Iterator<SelectionKey> readIterator = readSelector.selectedKeys().iterator();
                            while (readIterator.hasNext()) {
                                readKey = readIterator.next();
                                readIterator.remove();
                                if (readKey.isValid() && readKey.isReadable()) {
                                    doRead(readKey);
                                }
                                readKey = null;
                            }
                        } catch (InterruptedException e) {
                            if (running) {
                                Sys.p(getName() + " got InterruptedException in Reader "
                                        + StringUtils.stringifyException(e));
                            }
                        } catch (IOException ex) {
                            Sys.p(getName() + " got IOException in Reader ", ex);
                        }
                    }

                    try {
                        readSelector.close();
                    } catch (IOException e) {
                        Sys.p(e.getMessage());
                    }
                }

                Sys.p("SocketReader is stopping");
            }

            void startAdd() {
                adding = true;
                readSelector.wakeup();
            }

            synchronized void finishAdd() {
                adding = false;
                this.notify();
            }

            synchronized SelectionKey registerChannel(SocketChannel channel)
                    throws IOException {
                return channel.register(readSelector, SelectionKey.OP_READ);
            }
        }
    }

    private class Responder extends Thread {

        private Selector writeSelector;
        private int pending;

        final static int PURGE_INTERVAL = 900000; // 15mins

        Responder() throws IOException {
            writeSelector = Selector.open();
            pending = 0;

            this.setName("Responder");
            this.setDaemon(true);
        }

        public void run() {
            Sys.p(getName() + " is starting");

            long lastPurgeTime = 0;

            while (running) {
                try {
                    waitPending();
                    writeSelector.select(PURGE_INTERVAL);

                    Iterator<SelectionKey> writeIterator = writeSelector.selectedKeys().iterator();
                    while (writeIterator.hasNext()) {
                        SelectionKey writeKey = writeIterator.next();
                        writeIterator.remove();

                        try {
                            if (writeKey.isValid() && writeKey.isWritable()) {
                                doAsyncWrite(writeKey);
                            }
                        } catch (IOException e) {
                            Sys.p(getName() + " Got IOException in doAsyncWrite() " + e);
                        }
                    }

                    long now = System.currentTimeMillis();
                    if (now < lastPurgeTime + PURGE_INTERVAL) {
                        continue;
                    }

                    lastPurgeTime = now;

                    ArrayList<Call> calls;

                    synchronized (writeSelector.keys()) {
                        calls = new ArrayList<>(writeSelector.keys().size());
                        writeIterator = writeSelector.keys().iterator();
                        while (writeIterator.hasNext()) {
                            SelectionKey key = writeIterator.next();
                            Call call = (Call) key.attachment();
                            if (call != null && key.channel() == call.connection.channel) {
                                calls.add(call);
                            }
                        }
                    }

                    for (Call call : calls) {
                        try {
                            doPurge(call, now);
                        } catch (IOException e) {
                            Sys.p("Got IOException while purging old calls " + e);
                        }
                    }
                } catch (OutOfMemoryError e) {
                    Sys.p("Got OutOfMemoryError in Responder ", e);

                    try {
                        Thread.sleep(60000);
                    } catch (Exception ie) {
                        Sys.p(e.getMessage());
                    }
                } catch (Exception e) {
                    Sys.p("Got Exception in Responder " + StringUtils.stringifyException(e));
                }
            }

            Sys.p(getName() + " is stopping");
        }

        void doRespond(Call call) throws IOException {
            synchronized (call.connection.responseQueue) {
                call.connection.responseQueue.addLast(call);
                if (call.connection.responseQueue.size() == 1) {
                    processResponse(call.connection.responseQueue, true);
                }
            }
        }

        private void doPurge(Call call, long now) throws IOException {
            LinkedList<Call> responseQueue = call.connection.responseQueue;
            synchronized (responseQueue) {
                Iterator<Call> iter = responseQueue.listIterator(0);
                while (iter.hasNext()) {
                    call = iter.next();
                    if (now > call.timestamp + PURGE_INTERVAL) {
                        closeConnection(call.connection);
                        break;
                    }
                }
            }
        }

        private synchronized void waitPending() throws InterruptedException {
            while (pending > 0) {
                wait();
            }
        }

        private void doAsyncWrite(SelectionKey key) throws IOException {
            Call call = (Call) key.attachment();
            if (call == null) {
                return;
            }

            if (key.channel() != call.connection.channel) {
                throw new IOException("doAsyncWrite: bad channel");
            }

            synchronized (call.connection.responseQueue) {
                if (processResponse(call.connection.responseQueue, false)) {
                    try {
                        key.interestOps(0);
                    } catch (CancelledKeyException e) {
                        Sys.p("Exception while changing ops : " + e);
                    }
                }
            }
        }

        private boolean processResponse(LinkedList<Call> responseQueue,
                                        boolean inHandler) throws IOException {
            boolean error = true;
            boolean done = false;
            int numElements = 0;
            Call call = null;

            try {
                synchronized (responseQueue) {
                    numElements = responseQueue.size();
                    if (numElements == 0) {
                        error = false;
                        return true;
                    }

                    call = responseQueue.removeFirst();
                    SocketChannel channel = call.connection.channel;

                    int numBytes = channelWrite(channel, call.response);
                    if (numBytes < 0) {
                        return true;
                    }

                    Sys.p(getName() + ", responding to client "
                            + call.connection + " done " + numBytes + "bytes");

                    if (!call.response.hasRemaining()) {
                        call.connection.decConnCount();
                        done = (numElements == 1);
                    } else {
                        call.connection.responseQueue.addFirst(call);

                        if (inHandler) {
                            call.timestamp = System.currentTimeMillis();
                            incPending();

                            try {
                                writeSelector.wakeup();
                                channel.register(writeSelector, SelectionKey.OP_WRITE, call);
                            } catch (ClosedChannelException e) {
                                done = true;
                            } finally {
                                decPending();
                            }
                        }
                    }
                    error = false;
                }
            } finally {
                if (error && call != null) {
                    Sys.p(getName() + " Got error in call: " + call);
                    done = true;
                    closeConnection(call.connection);
                }
            }
            return done;
        }

        private synchronized void incPending() {
            pending++;
        }

        private synchronized void decPending() {
            pending--;
            notify();
        }
    }

    private static class Call {
        String str;
        private Connection connection;
        private long timestamp;

        private ByteBuffer response;

        Call(String str, Connection connection, Responder responder) {
            this.str = str;
            this.connection = connection;
            this.timestamp = System.currentTimeMillis();
            this.response = null;
        }

        synchronized void setResponse(ByteBuffer response) {
            this.response = response;
        }

        public String toString() {
            return connection.toString();
        }
    }

    private class Connection {

        private SocketChannel channel;
        private Socket socket;

        private String hostAddress;
        private int remotePort;

        private long lastContact;
        private volatile int connCount = 0;

        private boolean headerRead = false;

        private ByteBuffer data;
        private ByteBuffer dataLengthBuffer;
        private int dataLength;

        ConnectionHeader header = new ConnectionHeader();
        ConnectionBody body = new ConnectionBody();

        private LinkedList<Call> responseQueue;

        Connection(SelectionKey key, SocketChannel channel, long lastContact) {
            this.channel = channel;
            this.lastContact = lastContact;
            this.socket = channel.socket();
            InetAddress address = socket.getInetAddress();
            if (address == null) {
                this.hostAddress = "*Unknown*";
            } else {
                this.hostAddress = address.getHostAddress();
            }

            this.remotePort = socket.getPort();
            this.responseQueue = new LinkedList<>();
            this.data = null;
            this.dataLengthBuffer = ByteBuffer.allocate(4);
        }

        void setLastContact(long lastContact) {
            this.lastContact = lastContact;
        }

        int readAndProcess() throws IOException, InterruptedException {
            while (true) {
                int count = -1;
                if (dataLengthBuffer.remaining() > 0) {
                    count = channelRead(channel, dataLengthBuffer);
                    if (count < 0 || dataLengthBuffer.remaining() > 0)
                        return count;
                }

                if (data == null) {
                    dataLengthBuffer.flip();
                    dataLength = dataLengthBuffer.getInt();

                    if (dataLength == -1) {
                        // ping message
                        dataLengthBuffer.clear();
                        return 0;
                    }

                    data = ByteBuffer.allocate(dataLength);
                    incConnCount();
                }

                count = channelRead(channel, data);

                if (data.remaining() == 0) {
                    dataLengthBuffer.clear();
                    data.flip();

                    if (headerRead) {
                        processBody();
                        headerRead = false;
                        data = null;

                        return count;
                    } else {
                        processHeader();
                        headerRead = true;
                        data = null;

                        continue;
                    }
                }

                return count;
            }
        }

        private void processHeader() throws IOException {
            DataInputStream in =
                    new DataInputStream(new ByteArrayInputStream(data.array()));
            header.readFields(in);
        }

        private void processBody() throws IOException, InterruptedException {
            DataInputStream in =
                    new DataInputStream(new ByteArrayInputStream(data.array()));
            body.readFields(in);

            Call call = new Call(body.getBody(), this, responder);
            callQueue.put(call);
        }

        String getHostAddress() {
            return hostAddress;
        }

        private boolean timedOut(long currentTime) {
            return isIdle() && currentTime - lastContact > maxIdleTime;
        }

        private synchronized void close() throws IOException {
            if (!channel.isOpen()) {
                return;
            }

            try {
                socket.shutdownOutput();
            } catch (Exception e) {
                Sys.p(e.getMessage());
            }

            if (channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    Sys.p(e.getMessage());
                }
            }

            try {
                socket.close();
            } catch (Exception e) {
                Sys.p(e.getMessage());
            }
        }

        private boolean isIdle() {
            return connCount == 0;
        }

        private void decConnCount() {
            connCount--;
        }

        private void incConnCount() {
            connCount++;
        }

        public String toString() {
            return getHostAddress() + ":" + remotePort;
        }
    }

    private class Worker extends Thread {

        Worker(int sn) {
            this.setDaemon(true);
            this.setName("Worker" + sn);
        }

        public void run() {
            Sys.p(getName() + " is starting");
            ByteArrayOutputStream buf = new ByteArrayOutputStream(INITIAL_RESP_BUF_SIZE);
            while (running) {
                try {
                    final Call call = callQueue.take();
                    Sys.p(getName() + ", has a call from " + call.connection);
                    String error = null;
                    Writable body = new ConnectionBody("Hello client " + call.str);

                    try {
                        // process business Sys.pic
                        Sys.p(call.str);
                    } catch (Throwable e) {
                        Sys.p(getName() + " call " + call + " error: " + e, e);
                        error = StringUtils.stringifyException(e);
                    }

                    setupResponse(buf, call, (error == null) ? Status.SUCCESS : Status.ERROR, body, error);

                    if (buf.size() > maxRespSize) {
                        Sys.p("Large response size " + buf.size() + " for call " + call.toString());
                        buf = new ByteArrayOutputStream(INITIAL_RESP_BUF_SIZE);
                    }

                    responder.doRespond(call);
                } catch (InterruptedException e) {
                    if (running) {
                        Sys.p(getName() + " Got InterruptedException in worker " + StringUtils.stringifyException(e));
                    }
                } catch (Exception e) {
                    Sys.p(getName() + " Got Exception in worker " + StringUtils.stringifyException(e));
                }
            }

            Sys.p(getName() + " is stopping");
        }
    }
}
