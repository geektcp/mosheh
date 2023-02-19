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
package com.geektcp.common.core.system;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author geektcp on 2023/2/4 23:43.
 */
class ThyStream {

    private ThyStream() {
    }

    private static final PrintStream out = System.out;
    private static final PrintStream err = System.err;

    private static boolean isNull(Object str) {
        if (Objects.isNull(str)) {
            return true;
        }
        return false;
    }

    public static void setOut(PrintStream out) {
        System.setOut(out);
    }

    public static void print(Object str) {
        if (isNull(str)) {
            return;
        }
        out.println(str);
    }

    public static void println(Object str) {
        if (isNull(str)) {
            return;
        }
        out.println(str);
    }

    public static void p(Object str) {
        println(str);
    }

    public static void println(String str) {
        if (isNull(str)) {
            return;
        }
        out.println(str);
    }

    public static void print(String str) {
        if (isNull(str)) {
            return;
        }
        out.print(str);
    }

    public static void print(long n) {
        if (isNull(n)) {
            return;
        }
        out.print(n);
    }

    public static void printf(String format, Object... args) {
        if (isNull(format)) {
            return;
        }
        out.format(format, args);
    }

    public static void p(String messagePattern, Object... args) {
        String msg = buildMessage(messagePattern, args);
        p(msg);
    }

    public static String buildMessage(String messagePattern, Object[] args) {
        if (messagePattern == null) {
            return null;
        }
        int length = messagePattern.length();
        int result = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            char curChar = messagePattern.charAt(i);
            if (curChar == '{' && messagePattern.charAt(i + 1) == '}') {
                if (result < args.length) {
                    sb.append(args[result]);
                }
                result++;
                continue;
            }
            if (curChar == '}') {
                continue;
            }
            sb.append(curChar);
        }

        return sb.toString();
    }


}
