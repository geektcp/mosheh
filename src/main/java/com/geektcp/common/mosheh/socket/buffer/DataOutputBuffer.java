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
package com.geektcp.common.mosheh.socket.buffer;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author geektcp on 2023/2/22 1:14.
 */
public class DataOutputBuffer extends DataOutputStream {

    private static class Buffer extends ByteArrayOutputStream {

        byte[] getData() {
            return buf;
        }
        int getLength() {
            return count;
        }
        Buffer() {
            super();
        }
        Buffer(int size) {
            super(size);
        }

        public void write(DataInput in, int len) throws IOException {
            int newCount = count + len;
            if (newCount > buf.length) {
                byte[] newBuf = new byte[Math.max(buf.length << 1, newCount)];
                System.arraycopy(buf, 0, newBuf, 0, count);
                buf = newBuf;
            }
            in.readFully(buf, count, len);
            count = newCount;
        }
    }

    private Buffer buffer;

    public DataOutputBuffer() {
        this(new Buffer());
    }

    public DataOutputBuffer(int size) {
        this(new Buffer(size));
    }

    private DataOutputBuffer(Buffer buffer) {
        super(buffer);
        this.buffer = buffer;
    }

    public byte[] getData() {
        return buffer.getData();
    }

    public int getLength() {
        return buffer.getLength();
    }

    public DataOutputBuffer reset() {
        this.written = 0;
        buffer.reset();
        return this;
    }

    public void write(DataInput in, int length) throws IOException {
        buffer.write(in, length);
    }

    public void writeTo(OutputStream out) throws IOException {
        buffer.writeTo(out);
    }
}
