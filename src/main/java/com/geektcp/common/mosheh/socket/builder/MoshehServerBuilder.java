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

package com.geektcp.common.mosheh.socket.builder;

import com.geektcp.common.mosheh.socket.server.MoshehServer;
import com.geektcp.common.mosheh.system.Sys;

import java.io.IOException;

/**
 * @author geektcp on 2023/2/2 1:04.
 */
public class MoshehServerBuilder {

    private static String SERVER_IP = "127.0.0.1";
    private static int PORT = 10017;

    public static void start() throws IOException {
        Sys.p("mosheh Server started !!!");

        MoshehServer moshehServer = new MoshehServer(SERVER_IP, PORT);
        try {
            moshehServer.start();
        } catch (IOException e) {
            moshehServer.stop();
            throw e;
        }

        try {
            moshehServer.join();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
    }
}
