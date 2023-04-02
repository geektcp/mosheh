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
package com.geektcp.common.mosheh.concurrent.thread.executor;

import com.geektcp.common.mosheh.concurrent.thread.executor.service.TinyExecutorService;
import com.geektcp.common.mosheh.system.Sys;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author geektcp on 2023/2/6 22:47.
 */
public class ThyExecutor {

    private TinyExecutorService tinyExecutor;

    private Integer poolSize;
    private Integer timeout;
    private Integer duration;

    private static Integer poolSizeDefault = Sys.availableProcessors() - 1;
    private static Integer timeoutDefault = 40;
    private static Integer durationDefault = 20;

    private static ThyExecutor instance;

    public ThyExecutor(Integer poolSize, Integer timeout, Integer duration) {
        this.poolSize = poolSize;
        this.timeout = timeout;
        this.duration = duration;

        init();
    }

    public ThyExecutor(Integer poolSize) {
        this(poolSize, timeoutDefault, durationDefault);
    }

    public ThyExecutor() {
        this(poolSizeDefault, timeoutDefault, durationDefault);
    }

    public void init() {
        tinyExecutor = TinyExecutorBuilder.newFixedThreadPool(poolSize);
    }

    public static ThyExecutor getInstance() {
        if (Objects.isNull(instance)) {
            return new ThyExecutor();
        }
        return instance;
    }

    public TinyExecutorService getTinyExecutor() {
        return tinyExecutor;
    }

    /**
     * shutdown tinyExecutor
     */
    public void cleanup() {
        tinyExecutor.shutdown();
        boolean termination = false;
        while (!tinyExecutor.isTerminated()) {
            try {
                termination = tinyExecutor.awaitTermination(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException ignore) {
                // do noting
                Thread.currentThread().interrupt();
            }
        }
        if (!termination) {
            Sys.p("force to shutdown tinyExecutor after {} ms",
                    TimeUnit.MILLISECONDS.convert(duration, TimeUnit.SECONDS));
            tinyExecutor.shutdownNow();
        } else {
            Sys.p("tinyExecutor shutdown finished!");
        }
    }

    /**
     *
     * @param task a dest method which have a return value
     * @return execute the dest method in a thread pool
     *
     * example:
     *     List<Future<Long>> result = new ArrayList<>();
     *         for (int i = 0; i < 100; i++) {
     *             Future<Long> future = tinyExecutorService.submit(() -> (
     *                     getId()
     *             ));
     *             result.add(future);
     *         }
     */
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        tinyExecutor.execute(futureTask);
        return futureTask;
    }
}
