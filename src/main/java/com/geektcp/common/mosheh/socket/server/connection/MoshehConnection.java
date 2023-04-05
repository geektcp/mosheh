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
package com.geektcp.common.mosheh.socket.server.connection;

import com.geektcp.common.mosheh.socket.comparator.Writable;
import com.geektcp.common.mosheh.socket.constant.Status;
import com.geektcp.common.mosheh.socket.server.call.MoshehCall;
import com.geektcp.common.mosheh.socket.server.responder.MoshehResponder;
import com.geektcp.common.mosheh.socket.text.ConnectionBody;
import com.geektcp.common.mosheh.socket.text.ConnectionHeader;
import com.geektcp.common.mosheh.socket.util.WritableUtils;
import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.*;

/**
 * @author geektcp on 2023/4/5 20:58.
 */

public class MoshehConnection {

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

    private LinkedList<MoshehCall> responseQueue;


    private BlockingQueue<MoshehCall> callQueue;
    private MoshehResponder responder;

    public MoshehConnection(SelectionKey key,
                            SocketChannel channel,
                            long lastContact,
                            MoshehResponder responder,
                            BlockingQueue<MoshehCall> callQueue,
                            List<MoshehConnection> connectionList) {
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
        this.responder = responder;
        this.callQueue = callQueue;
    }

    public void setLastContact(long lastContact) {
        this.lastContact = lastContact;
    }

    public int readAndProcess() throws IOException, InterruptedException {
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

    private static int channelRead(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
        return (buffer.remaining() <= NIO_BUFFER_LIMIT) ?
                channel.read(buffer) : channelIO(channel, null, buffer);
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

        MoshehCall call = new MoshehCall(body.getBody(), this, responder);
        callQueue.put(call);
    }




    public static int channelIO(ReadableByteChannel readCh,
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


    public String getHostAddress() {
        return hostAddress;
    }

    public boolean timedOut(long currentTime) {
        return isIdle() && currentTime - lastContact > maxIdleTime;
    }

    public synchronized void close() {
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

    public void decConnCount() {
        connCount--;
    }

    private void incConnCount() {
        connCount++;
    }

    public String toString() {
        return getHostAddress() + ":" + remotePort;
    }

    public LinkedList<MoshehCall> getResponseQueue(){
        return responseQueue;
    }

    public SocketChannel getChannel(){
        return channel;
    }


    public static void closeConnection(MoshehConnection connection) {
        synchronized (connectionList) {
            if (connectionList.remove(connection)) {
                numConnections--;
            }
        }
        connection.close();
    }
}
