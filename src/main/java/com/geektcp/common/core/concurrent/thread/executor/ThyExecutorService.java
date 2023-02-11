package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.concurrent.thread.able.ThyRunnable;
import com.geektcp.common.core.system.Sys;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/6 22:47.
 */
public class ThyExecutorService {

    private ExecutorService asyncExecutor;

    private Integer poolSize;
    private Integer timeout;
    private Integer duration;

    private static Integer poolSizeDefault = Sys.availableProcessors() - 1;
    private static Integer timeoutDefault = 40;
    private static Integer durationDefault = 20;


    public ThyExecutorService(Integer poolSize, Integer timeout, Integer duration) {
        this.poolSize = poolSize;
        this.timeout = timeout;
        this.duration = duration;
    }

    public ThyExecutorService(Integer poolSize) {
        this(poolSize, timeoutDefault, durationDefault);
    }

    public ThyExecutorService() {
        this(poolSizeDefault, timeoutDefault, durationDefault);
    }


    public void init() {
        asyncExecutor = Executors.newFixedThreadPool(poolSize);
    }

    public void cleanup() {
        asyncExecutor.shutdown();
        boolean termination = false;
        while (!asyncExecutor.isTerminated()) {
            try {
                termination = asyncExecutor.awaitTermination(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException ignore) {
                // do noting
                Thread.currentThread().interrupt();
            }
        }
        if (!termination) {
            Sys.printf("force to shutdown asyncExecutor after %d ms" +
                    TimeUnit.MILLISECONDS.convert(duration, TimeUnit.SECONDS));
            asyncExecutor.shutdownNow();
        } else {
            Sys.p("easy shutdown asyncExecutor");
        }
    }

    public void submit() {
        Future future = asyncExecutor.submit(new ThyRunnable());
        try {
            Object result = future.get(timeout, TimeUnit.SECONDS);
            Sys.p(result);
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

    }


}
