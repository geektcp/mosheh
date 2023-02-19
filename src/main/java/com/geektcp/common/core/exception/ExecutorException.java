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
package com.geektcp.common.core.exception;

public class ExecutorException extends Exception {
    private static final long serialVersionUID = 7830266012832686185L;

    /**
     * Constructs an {@code ExecutorException} with no detail message.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     */
    public ExecutorException() { }

    /**
     * Constructs an {@code ExecutorException} with the specified detail
     * message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     *
     * @param message the detail message
     */
    public ExecutorException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ExecutorException} with the specified detail
     * message and cause.
     *
     * @param  message the detail message
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ExecutorException} with the specified cause.
     * The detail message is set to {@code (cause == null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public ExecutorException(Throwable cause) {
        super(cause);
    }
}
