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
package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.concurrent.thread.executor.service.TinyExecutorService;
import com.geektcp.common.core.concurrent.thread.executor.service.impl.extend.TinyScheduledExecutor;
import com.geektcp.common.core.concurrent.thread.executor.service.impl.extend.TinyFinalizeExecutor;
import com.geektcp.common.core.concurrent.thread.executor.service.impl.extend.TinyDelegatedExecutor;
import com.geektcp.common.core.concurrent.thread.executor.service.impl.extend.TinyThreadPoolExecutor;

import java.util.concurrent.*;

/**
 * @author geektcp on 2023/2/6 22:44.
 */
public class TinyExecutors {

    private TinyExecutors() {

    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
         return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory);
    }

    public static ExecutorService newWorkStealingPool(int parallelism) {
        return new ForkJoinPool(
                parallelism,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true);
    }

    public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool
                (Runtime.getRuntime().availableProcessors(),
                        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                        null, true);
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory);
    }

    public static ExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public static ExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }


    /**
     *
     * @return TinyExecutorService
     */
    public static TinyExecutorService newSingleThreadScheduledExecutor() {
        return new TinyScheduledExecutor
                (new ScheduledThreadPoolExecutor(1));
    }

    public static TinyExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        return new TinyScheduledExecutor
                (new ScheduledThreadPoolExecutor(1, threadFactory));
    }

    public static TinyExecutorService newSingleThreadExecutor() {
        return new TinyFinalizeExecutor
                (new TinyThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>()));
    }

    public static TinyExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return new TinyFinalizeExecutor
                (new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        threadFactory));
    }

    public static TinyExecutorService newTinyExecutor(ExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException();
        }
        return new TinyDelegatedExecutor(executor);
    }

}
