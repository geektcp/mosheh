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
package com.geektcp.common.mosheh.socket.server.call;

import com.geektcp.common.mosheh.socket.server.connection.MoshehConnection;
import com.geektcp.common.mosheh.socket.server.responder.MoshehResponder;

import java.nio.ByteBuffer;

/**
 * @author geektcp on 2023/4/5 20:57.
 */

public class MoshehCall {

    String str;
    private MoshehConnection connection;
    private long timestamp;

    private ByteBuffer response;

    public MoshehCall(String str, MoshehConnection connection, MoshehResponder responder) {
        this.str = str;
        this.connection = connection;
        this.timestamp = System.currentTimeMillis();
        this.response = null;
    }

    public synchronized void setResponse(ByteBuffer response) {
        this.response = response;
    }

    public String toString() {
        return connection.toString();
    }

    public MoshehConnection getConnection(){
        return connection;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public String getStr(){
        return str;
    }


    public ByteBuffer getResponse(){
        return response;
    }
}
