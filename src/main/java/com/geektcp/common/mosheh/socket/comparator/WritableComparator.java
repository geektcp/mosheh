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
package com.geektcp.common.mosheh.socket.comparator;

import java.io.IOException;
import java.util.HashMap;
/**
 * @author geektcp on 2018/2/22 1:14.
 */
public class WritableComparator implements RawComparator {
    private static HashMap<Class, WritableComparator> comparators = new HashMap<>();

    public static synchronized void define(Class c,
                                           WritableComparator comparator) {
        comparators.put(c, comparator);
    }

    public int compare(WritableComparable a, WritableComparable b) {
        return a.compareTo(b);
    }

    public int compare(Object a, Object b) {
        return compare((WritableComparable) a, (WritableComparable) b);
    }

    public static int compareBytes(byte[] b1, int s1, int l1,
                                   byte[] b2, int s2, int l2) {
        int end1 = s1 + l1;
        int end2 = s2 + l2;
        for (int i = s1, j = s2; i < end1 && j < end2; i++, j++) {
            int a = (b1[i] & 0xff);
            int b = (b2[j] & 0xff);
            if (a != b) {
                return a - b;
            }
        }

        return l1 - l2;
    }

    public static int hashBytes(byte[] bytes, int length) {
        int hash = 1;
        for (int i = 0; i < length; i++) {
            hash = (31 * hash) + (int) bytes[i];
        }

        return hash;
    }

    public static int readUnsignedShort(byte[] bytes, int start) {
        return (((bytes[start] & 0xff) << 8) +
                ((bytes[start + 1] & 0xff)));
    }

    public static int readInt(byte[] bytes, int start) {
        return (((bytes[start] & 0xff) << 24) +
                ((bytes[start + 1] & 0xff) << 16) +
                ((bytes[start + 2] & 0xff) << 8) +
                ((bytes[start + 3] & 0xff)));

    }

    public static float readFloat(byte[] bytes, int start) {
        return Float.intBitsToFloat(readInt(bytes, start));
    }

    public static long readLong(byte[] bytes, int start) {
        return ((long) (readInt(bytes, start)) << 32)
                + (readInt(bytes, start + 4) & 0xFFFFFFFFL);
    }

    public static double readDouble(byte[] bytes, int start) {
        return Double.longBitsToDouble(readLong(bytes, start));
    }

    public static long readVLong(byte[] bytes, int start) throws IOException {
        int len = bytes[start];
        if (len >= -112) {
            return len;
        }
        boolean isNegative = (len < -120);
        len = isNegative ? -(len + 120) : -(len + 112);
        if (start + 1 + len > bytes.length) {
            throw new IOException("Not enough number of bytes for a zero-compressed integer");
        }

        long i = 0;
        for (int idx = 0; idx < len; idx++) {
            i = i << 8;
            i = i | (bytes[start + 1 + idx] & 0xFF);
        }

        return (isNegative ? (i ^ -1L) : i);
    }

    public static int readVInt(byte[] bytes, int start) throws IOException {
        return (int) readVLong(bytes, start);
    }

    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        return 0;
    }
}
