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
package com.geektcp.common.mosheh.socket.server.config;

import com.geektcp.common.mosheh.socket.server.connection.MoshehConnection;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * @author geektcp on 2023/4/5 21:13.
 */
@Data
public class MoshehConfig {

    public static String IP = "127.0.0.1";
    public static int PORT = 8090;

    public static int numConnections = 0;


    public static final int readThreads = 5;
    public static final int workerThreads = 10;
    public static final int thresholdIdleConnections = 4000;
    public static final int maxConnectionsToNuke = 10;
    public static final int maxIdleTime = 2000;

    public static final int NIO_BUFFER_LIMIT = 8192;
    public static final int INITIAL_RESP_BUF_SIZE = 10240;

    public static final int maxRespSize = 1024 * 1024;
    public static final int callQueueCapacity = 4096 * 100;



    ///////////////////////////////////
    public static volatile boolean running = true;


    public static List<MoshehConnection> connectionList = Collections.synchronizedList(new LinkedList<>());
}
