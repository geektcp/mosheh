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
package com.geektcp.common.mosheh.socket.util;


import com.geektcp.common.mosheh.exception.BaseException;
import com.geektcp.common.mosheh.socket.text.Text;
import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author geektcp on 2018/2/22 1:14.
 */
public final class WritableUtils {

    private WritableUtils() {
    }

    public static byte[] readCompressedByteArray(DataInput in)
            throws IOException {
        int length = in.readInt();
        if (length == -1) {
            return null;
        }

        byte[] buffer = new byte[length];
        in.readFully(buffer);

        GZIPInputStream gzi = new GZIPInputStream(new ByteArrayInputStream(buffer, 0, buffer.length));
        byte[] outBuf = new byte[length];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int len;
        while ((len = gzi.read(outBuf, 0, outBuf.length)) != -1) {
            bos.write(outBuf, 0, len);
        }

        byte[] decompressed = bos.toByteArray();
        bos.close();
        gzi.close();

        return decompressed;
    }

    public static void skipCompressedByteArray(DataInput in)
            throws IOException {
        int length = in.readInt();
        if (length != -1) {
            skipFully(in, length);
        }
    }

    public static int writeCompressedByteArray(DataOutput out, byte[] bytes)
            throws IOException {
        if (bytes != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzout = new GZIPOutputStream(bos);
            gzout.write(bytes, 0, bytes.length);
            gzout.close();

            byte[] buffer = bos.toByteArray();
            int len = buffer.length;
            out.writeInt(len);
            out.write(buffer, 0, len);

            return ((bytes.length != 0) ? (100 * buffer.length) / bytes.length : 0);
        } else {
            out.writeInt(-1);
            return -1;
        }
    }

    public static String readCompressedString(DataInput in) throws IOException {
        byte[] bytes = readCompressedByteArray(in);
        if (bytes == null) return null;

        return new String(bytes, "UTF-8");
    }

    public static int writeCompressedString(DataOutput out, String s)
            throws IOException {
        return writeCompressedByteArray(out, (s != null) ? s.getBytes("UTF-8") : null);
    }

    public static void writeString(DataOutput out, String s) throws IOException {
        if (s != null) {
            byte[] buffer = s.getBytes("UTF-8");
            int len = buffer.length;
            out.writeInt(len);
            out.write(buffer, 0, len);
        } else {
            out.writeInt(-1);
        }
    }

    public static String readString(DataInput in) throws IOException {
        int length = in.readInt();
        if (length == -1) {
            return null;
        }

        byte[] buffer = new byte[length];
        in.readFully(buffer);

        return new String(buffer, "UTF-8");
    }

    public static void writeStringArray(DataOutput out, String[] s)
            throws IOException {
        out.writeInt(s.length);
        for (int i = 0; i < s.length; i++) {
            writeString(out, s[i]);
        }
    }

    public static void writeCompressedStringArray(DataOutput out, String[] s)
            throws IOException {
        if (s == null) {
            out.writeInt(-1);
            return;
        }

        out.writeInt(s.length);
        for (int i = 0; i < s.length; i++) {
            writeCompressedString(out, s[i]);
        }
    }

    public static String[] readStringArray(DataInput in) throws IOException {
        int len = in.readInt();
        if (len == -1) {
            return null;
        }

        String[] s = new String[len];
        for (int i = 0; i < len; i++) {
            s[i] = readString(in);
        }

        return s;
    }

    public static String[] readCompressedStringArray(DataInput in)
            throws IOException {
        int len = in.readInt();
        if (len == -1) {
            return null;
        }

        String[] s = new String[len];
        for (int i = 0; i < len; i++) {
            s[i] = readCompressedString(in);
        }

        return s;
    }

    public static void displayByteArray(byte[] record) {
        int i;
        for (i = 0; i < record.length - 1; i++) {
            if (i % 16 == 0) {
                Sys.p(i);
            }

            Sys.p(Integer.toHexString(record[i] >> 4 & 0x0F));
            Sys.p(Integer.toHexString(record[i] & 0x0F));
            Sys.p(",");
        }

        Sys.p(Integer.toHexString(record[i] >> 4 & 0x0F));
        Sys.p(Integer.toHexString(record[i] & 0x0F));
        Sys.println();
    }

    public static void writeVInt(DataOutput stream, int i) throws IOException {
        writeVLong(stream, i);
    }

    public static void writeVLong(DataOutput stream, long i) throws IOException {
        if (i >= -112 && i <= 127) {
            stream.writeByte((byte) i);
            return;
        }

        int len = -112;
        if (i < 0) {
            i ^= -1L;
            len = -120;
        }

        long tmp = i;
        while (tmp != 0) {
            tmp = tmp >> 8;
            len--;
        }

        stream.writeByte((byte) len);

        len = (len < -120) ? -(len + 120) : -(len + 112);

        for (int idx = len; idx != 0; idx--) {
            int shiftbits = (idx - 1) * 8;
            long mask = 0xFFL << shiftbits;
            stream.writeByte((byte) ((i & mask) >> shiftbits));
        }
    }

    public static long readVLong(DataInput stream) throws IOException {
        byte firstByte = stream.readByte();
        int len = decodeVIntSize(firstByte);
        if (len == 1) {
            return firstByte;
        }

        long i = 0;
        for (int idx = 0; idx < len - 1; idx++) {
            byte b = stream.readByte();
            i = i << 8;
            i = i | (b & 0xFF);
        }

        return (isNegativeVInt(firstByte) ? (i ^ -1L) : i);
    }

    public static int readVInt(DataInput stream) throws IOException {
        return (int) readVLong(stream);
    }

    public static boolean isNegativeVInt(byte value) {
        return value < -120 || (value >= -112 && value < 0);
    }

    public static int decodeVIntSize(byte value) {
        if (value >= -112) {
            return 1;
        } else if (value < -120) {
            return -119 - value;
        }

        return -111 - value;
    }

    public static int getVIntSize(long i) {
        if (i >= -112 && i <= 127) {
            return 1;
        }

        if (i < 0) {
            i ^= -1L;
        }

        int dataBits = Long.SIZE - Long.numberOfLeadingZeros(i);

        return (dataBits + 7) / 8 + 1;
    }

    public static <T extends Enum<T>> T readEnum(DataInput in, Class<T> enumType)
            throws IOException {
        return T.valueOf(enumType, Text.readString(in));
    }

    public static void writeEnum(DataOutput out, Enum<?> enumVal)
            throws IOException {
        Text.writeString(out, enumVal.name());
    }

    public static void skipFully(DataInput in, int len) throws IOException {
        int total = 0;
        int cur = 0;

        while ((total < len) && ((cur = in.skipBytes(len - total)) > 0)) {
            total += cur;
        }

        if (total < len) {
            throw new IOException("Not able to skip " + len
                    + " bytes, possibly " + "due to end of input.");
        }
    }

    public static String readStringSafely(DataInput in, int maxLength)
            throws IOException {
        int length = readVInt(in);
        if (length < 0 || length > maxLength) {
            throw new BaseException(
                    "Encoded byte size for String was " + length +
                            ", which is outside of 0.." +
                            maxLength + " range.");
        }

        byte[] bytes = new byte[length];
        in.readFully(bytes, 0, length);

        return Text.decode(bytes);
    }
}
