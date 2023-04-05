/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geektcp.common.mosheh.socket.server.listener;

import com.geektcp.common.mosheh.socket.server.call.MoshehCall;
import com.geektcp.common.mosheh.socket.server.connection.MoshehConnection;
import com.geektcp.common.mosheh.socket.server.responder.MoshehResponder;
import com.geektcp.common.mosheh.socket.util.StringUtils;
import com.geektcp.common.mosheh.system.Sys;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;

import static com.geektcp.common.mosheh.socket.server.MoshehServer.bind;


import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.*;
import static com.geektcp.common.mosheh.socket.server.connection.MoshehConnection.closeConnection;

/**
 * @author geektcp on 2023/4/5 20:52.
 */

public class MoshehListener extends Thread {

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


    private BlockingQueue<MoshehCall> callQueue;
    private MoshehResponder responder;

    public MoshehListener() {
        try {
            address = new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT);

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
        }catch (Exception e){
            Sys.p(e.getMessage());
        }
    }

    public void run() {
        Sys.p(getName() + " is running on " + PORT);

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
        MoshehConnection conn = null;
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
                conn = new MoshehConnection(readKey, channel, System.currentTimeMillis(), responder, callQueue, connectionList);
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
        MoshehConnection conn = (MoshehConnection) key.attachment();
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
            MoshehConnection c = (MoshehConnection) key.attachment();
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
                MoshehConnection c;
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

    public synchronized void doStop() {
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
