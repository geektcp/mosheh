package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.concurrent.thread.able.ThyRunnable;
import com.geektcp.common.core.system.Sys;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/6 22:47.
 */
public class TinyExecutorService {

    private ExecutorService tinyExecutor;

    private Integer poolSize;
    private Integer timeout;
    private Integer duration;

    private static Integer poolSizeDefault = Sys.availableProcessors() - 1;
    private static Integer timeoutDefault = 40;
    private static Integer durationDefault = 20;

    private static TinyExecutorService instance;

    public TinyExecutorService(Integer poolSize, Integer timeout, Integer duration) {
        this.poolSize = poolSize;
        this.timeout = timeout;
        this.duration = duration;

        init();
    }

    public TinyExecutorService(Integer poolSize) {
        this(poolSize, timeoutDefault, durationDefault);
    }

    public TinyExecutorService() {
        this(poolSizeDefault, timeoutDefault, durationDefault);
    }

    public static TinyExecutorService getInstance(){
        if(Objects.isNull(instance)) {
            return new TinyExecutorService();
        }

        return instance;
    }


    public void init() {
        tinyExecutor = Executors.newFixedThreadPool(poolSize);
    }

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

    public void submit() {
        Future future = tinyExecutor.submit(new ThyRunnable());
        try {
            Object result = future.get(timeout, TimeUnit.SECONDS);
            Sys.p(result);
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

    }

    public ExecutorService getTinyExecutor() {
        return tinyExecutor;
    }



}
