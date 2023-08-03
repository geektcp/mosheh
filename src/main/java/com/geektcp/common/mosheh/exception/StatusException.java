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

/**
 * @author geektcp on 2018/5/16.
 */
public class StatusException extends BaseException {

    private Status status;

    public StatusException(Status status) {
        this(status, new String[]{});
    }

    public StatusException(Status status, Object... args) {
        this(status, null, args);
    }

    public StatusException(Status status, Throwable cause) {
        super(status.getCode(), status.getDesc(), cause);
        this.status = status;
    }

    public StatusException(Status status, Throwable cause, Object... args) {
        super(status.getCode(), status.getDesc(), cause, args);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
