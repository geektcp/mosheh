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

package com.geektcp.common.mosheh.exception;

import com.geektcp.common.mosheh.constant.Status;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author geektcp on 2018/5/16.
 */

public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1000000L;

    private static final String EXCEPTION_SEPARATOR = "|";

    private final int code;
    private final String desc;
    private final transient Object[] args;

    public BaseException() {
        this.code = 500;
        this.desc = "";
        this.args = new Object[]{};
    }

    public BaseException(Throwable t) {
        this.code = 500;
        this.desc = t.getMessage();
        this.args = new Object[]{};
    }

    public BaseException(Exception e) {
        this.code = 500;
        this.desc = e.getMessage();
        this.args = new Object[]{};
    }

    public BaseException(String desc) {
        this.code = 500;
        this.desc = "";
        this.args = new Object[]{};
    }

    public BaseException(Status exceptionStatus, Object... args) {
        super(exceptionStatus.getCode() + EXCEPTION_SEPARATOR + format(exceptionStatus.getDesc(), args));
        this.code = exceptionStatus.getCode();
        this.desc = exceptionStatus.getDesc();
        this.args = args;
    }

    public BaseException(int code, String desc) {
        super(code + EXCEPTION_SEPARATOR + desc);
        this.code = code;
        this.desc = desc;
        this.args = new Object[]{};
    }

    public BaseException(int code, String descPattern, Object... args) {
        super(code + EXCEPTION_SEPARATOR + format(descPattern, args));
        this.code = code;
        this.desc = descPattern;
        this.args = args;
    }

    public BaseException(int code, String desc, Throwable source) {
        super(code + EXCEPTION_SEPARATOR + desc, source);
        this.code = code;
        this.desc = desc;
        this.args = new Object[]{};
    }

    public BaseException(int code, String descPattern, Throwable source, Object... args) {
        super(code + EXCEPTION_SEPARATOR + format(descPattern, args), source);
        this.code = code;
        this.desc = descPattern;
        this.args = args;
    }

    public BaseException(String descPattern, Throwable source, Object... args) {
        this(-1, descPattern, source, args);
    }

    public BaseException(Status exceptionStatus) {
        this(exceptionStatus.getCode(), exceptionStatus.toString() + EXCEPTION_SEPARATOR + exceptionStatus.getDesc());
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        if (args == null) {
            return desc;
        }
        return format(desc, args);
    }

    public static BaseException newException(String desc) {
        return new BaseException(desc);
    }

    public static BaseException newException(Exception e) {
        return new BaseException(e);
    }


    private static String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }
}
