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
package com.geektcp.common.mosheh.socket.server.worker;

import com.geektcp.common.mosheh.socket.comparator.Writable;
import com.geektcp.common.mosheh.socket.constant.Status;
import com.geektcp.common.mosheh.socket.server.call.MoshehCall;
import com.geektcp.common.mosheh.socket.server.responder.MoshehResponder;
import com.geektcp.common.mosheh.socket.text.ConnectionBody;
import com.geektcp.common.mosheh.socket.util.StringUtils;
import com.geektcp.common.mosheh.socket.util.WritableUtils;
import com.geektcp.common.mosheh.system.Sys;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.INITIAL_RESP_BUF_SIZE;
import static com.geektcp.common.mosheh.socket.server.config.MoshehConfig.maxRespSize;

/**
 * @author geektcp on 2023/4/5 21:12.
 */

public class MoshehWorker extends Thread {

    private volatile boolean running;
    private BlockingQueue<MoshehCall> callQueue;
    private MoshehResponder responder;

    public MoshehWorker(int sn, boolean running, BlockingQueue<MoshehCall> callQueue, MoshehResponder responder) {
        this.setDaemon(true);
        this.setName("Worker" + sn);
        this.running = running;
        this.callQueue = callQueue;
        this.responder = responder;
    }

    public void run() {
        Sys.p(getName() + " is starting");
        ByteArrayOutputStream buf = new ByteArrayOutputStream(INITIAL_RESP_BUF_SIZE);
        while (running) {
            try {
                final MoshehCall call = callQueue.take();
                Sys.p(getName() + ", has a call from " + call.getConnection());
                String error = null;
                Writable body = new ConnectionBody("Hello client " + call.getStr());

                try {
                    // process business Sys.pic
                    Sys.p(call.getStr());
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

    private void setupResponse(ByteArrayOutputStream response, MoshehCall call, Status status, Writable w, String error)
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

}
