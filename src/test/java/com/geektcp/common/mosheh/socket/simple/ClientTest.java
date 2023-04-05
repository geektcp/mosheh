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
package com.geektcp.common.mosheh.socket.simple;

import com.geektcp.common.mosheh.system.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by geektcp on 2018/11/24.
 */

public class ClientTest {
    public static void main(String[] args) throws Exception {
        for (int i = 3300; i < 10000; i++) {
            Sys.p("start check port: {}", i);
            Socket socket = new Socket();
            socket.setSoTimeout(11);
            socket.connect(new InetSocketAddress("geektcp.com", i), 200);
            Sys.p("port is opened: {}", i);
            socket.close();


        }
    }


    @Test
    public void connect() throws Exception {
        Socket socket = new Socket();
        socket.setSoTimeout(11);
        socket.connect(new InetSocketAddress("localhost", 999), 200);
        Sys.p("port is opened: {}", 999);

        Assert.assertTrue(true);
    }

    @Test
    public void connectNIO() throws IOException {
        Socket socket = new Socket("127.0.0.1", 999);
        OutputStream out = socket.getOutputStream();
        String s = "hello world";
        out.write(s.getBytes());
        out.close();

        Assert.assertTrue(true);
    }

}
