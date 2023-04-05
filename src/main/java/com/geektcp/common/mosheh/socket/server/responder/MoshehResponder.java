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
package com.geektcp.common.mosheh.socket.server.responder;

import com.geektcp.common.mosheh.socket.server.call.MoshehCall;
import com.geektcp.common.mosheh.socket.server.connection.MoshehConnection;
import com.geektcp.common.mosheh.socket.util.StringUtils;
import com.geektcp.common.mosheh.system.Sys;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.*;
import static com.geektcp.common.mosheh.socket.server.connection.MoshehConnection.channelIO;
import static com.geektcp.common.mosheh.socket.server.connection.MoshehConnection.closeConnection;

/**
 * @author geektcp on 2023/4/5 20:55.
 */

public class MoshehResponder extends Thread {

    private volatile boolean running;
    private Selector writeSelector;
    private int pending;

    final static int PURGE_INTERVAL = 900000; // 15mins

    private List<MoshehConnection> connectionList;

    public MoshehResponder(boolean running) {
        try {
            writeSelector = Selector.open();
            pending = 0;

            this.setName("Responder");
            this.setDaemon(true);
            this.running = running;
        }catch (Exception e){
            Sys.p(e.getMessage());
        }

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

                ArrayList<MoshehCall> calls;

                synchronized (writeSelector.keys()) {
                    calls = new ArrayList<>(writeSelector.keys().size());
                    writeIterator = writeSelector.keys().iterator();
                    while (writeIterator.hasNext()) {
                        SelectionKey key = writeIterator.next();
                        MoshehCall call = (MoshehCall) key.attachment();
                        if (call != null && key.channel() == call.getConnection().getChannel()) {
                            calls.add(call);
                        }
                    }
                }

                for (MoshehCall call : calls) {
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

    public void doRespond(MoshehCall call) throws IOException {
        LinkedList<MoshehCall>  moshehCallLinkedList = call.getConnection().getResponseQueue();
        synchronized (moshehCallLinkedList) {
            moshehCallLinkedList.addLast(call);
            if (moshehCallLinkedList.size() == 1) {
                processResponse(moshehCallLinkedList, true);
            }
        }
    }

    private void doPurge(MoshehCall call, long now) throws IOException {
        LinkedList<MoshehCall>  moshehCallLinkedList = call.getConnection().getResponseQueue();
        synchronized (moshehCallLinkedList) {
            Iterator<MoshehCall> iter = moshehCallLinkedList.listIterator(0);
            while (iter.hasNext()) {
                call = iter.next();
                if (now > call.getTimestamp() + PURGE_INTERVAL) {
                    closeConnection(call.getConnection());
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
        MoshehCall call = (MoshehCall) key.attachment();
        if (call == null) {
            return;
        }

        if (key.channel() != call.getConnection().getChannel()) {
            throw new IOException("doAsyncWrite: bad channel");
        }

        synchronized (call.getConnection().getResponseQueue()) {
            if (processResponse(call.getConnection().getResponseQueue(), false)) {
                try {
                    key.interestOps(0);
                } catch (CancelledKeyException e) {
                    Sys.p("Exception while changing ops : " + e);
                }
            }
        }
    }

    private boolean processResponse(LinkedList<MoshehCall> responseQueue,
                                    boolean inHandler) throws IOException {
        boolean error = true;
        boolean done = false;
        int numElements = 0;
        MoshehCall call = null;

        try {
            synchronized (responseQueue) {
                numElements = responseQueue.size();
                if (numElements == 0) {
                    error = false;
                    return true;
                }

                call = responseQueue.removeFirst();
                SocketChannel channel = call.getConnection().getChannel();

                int numBytes = channelWrite(channel, call.getResponse());
                if (numBytes < 0) {
                    return true;
                }

                Sys.p(getName() + ", responding to client "
                        + call.getConnection() + " done " + numBytes + "bytes");

                if (!call.getResponse().hasRemaining()) {
                    call.getConnection().decConnCount();
                    done = (numElements == 1);
                } else {
                    call.getConnection().getResponseQueue().addFirst(call);

                    if (inHandler) {
                        call.setTimestamp(System.currentTimeMillis());
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
                closeConnection(call.getConnection());
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

    private static int channelWrite(WritableByteChannel channel, ByteBuffer buffer) throws IOException {
        return (buffer.remaining() <= NIO_BUFFER_LIMIT) ?
                channel.write(buffer) : channelIO(null, channel, buffer);
    }
}

