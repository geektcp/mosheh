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
package com.geektcp.common.mosheh.socket.server;

import com.geektcp.common.mosheh.socket.server.call.MoshehCall;
import com.geektcp.common.mosheh.socket.server.config.MoshehConfig;
import com.geektcp.common.mosheh.socket.server.listener.MoshehListener;
import com.geektcp.common.mosheh.socket.server.responder.MoshehResponder;
import com.geektcp.common.mosheh.socket.server.worker.MoshehWorker;
import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.*;


/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class MoshehServer {

    private MoshehListener listener = null;
    private MoshehResponder responder = null;
    private MoshehWorker[] workers = null;


    private LinkedBlockingQueue<MoshehCall> callQueue;
    private MoshehListener moshehListener;
    private MoshehResponder moshehResponder;

    public MoshehServer() throws IOException {
        this("127.0.0.1", 100017);
    }

    public MoshehServer(int port) throws IOException {
        this("127.0.0.1", port);
    }

    public MoshehServer(String ip, int port) {
        MoshehConfig.IP = ip;
        MoshehConfig.PORT = port;
        this.callQueue = new LinkedBlockingQueue<>(callQueueCapacity);
        this.listener = new MoshehListener();
        this.responder = new MoshehResponder(running);
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


    public synchronized void start() throws IOException {
        workers = new MoshehWorker[workerThreads];
        for (int i = 0; i < workerThreads; i++) {
            workers[i] = new MoshehWorker(i, running, callQueue, responder);
            workers[i].start();
        }

        responder.start();
        listener.start();
    }

    public synchronized void stop() {
        Sys.p("Stopping server on " + PORT);

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

    public boolean getRunning() {
        return running;
    }


}
