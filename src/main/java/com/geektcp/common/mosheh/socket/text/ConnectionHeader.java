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

package com.geektcp.common.mosheh.socket.text;

import com.geektcp.common.mosheh.socket.comparator.Writable;
import com.geektcp.common.mosheh.system.Sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class ConnectionHeader implements Writable {

    private String protocol;

    public ConnectionHeader() {

    }

    public ConnectionHeader(String protocol) {
        this.protocol = protocol;
    }

    public void readFields(DataInput in) throws IOException {
        protocol = Text.readString(in);
        if (protocol.isEmpty()) {
            protocol = null;
        } else {
            Sys.p("The protocol is: {}", protocol);
        }
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out, (protocol == null) ? "" : protocol);
    }
}
