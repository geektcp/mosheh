package com.geektcp.common.core.concurrent.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author geektcp on 2023/2/6 22:44.
 */
public class TinyExecutorBuilder {

    private TinyExecutorBuilder() {

    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return Executors.newFixedThreadPool(nThreads, threadFactory);
    }

    public static ExecutorService newWorkStealingPool(int parallelism) {
        return Executors.newWorkStealingPool(parallelism);
    }

    public static ExecutorService newWorkStealingPool() {
        return Executors.newWorkStealingPool();
    }

    public static ExecutorService newSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadExecutor(threadFactory);
    }

    public static ExecutorService newCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    public static ExecutorService newSingleThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    public static ExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    public static ExecutorService newScheduledThreadPool(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize);
    }

    public static ExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

    public static ExecutorService newTinyThreadPool(ExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException();
        }
        return new TinyExecutor(executor);
    }


}
