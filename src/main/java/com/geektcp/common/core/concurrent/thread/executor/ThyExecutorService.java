package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.system.Sys;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/6 22:47.
 */
public class ThyExecutorService {

    private ExecutorService asyncExecutor;

    private static Integer poolSize = Sys.availableProcessors() - 1;
    private static Integer timeout = 40;
    private static Integer duration = 20;


    @PostConstruct
    public void postConstruct() {
        asyncExecutor = Executors.newFixedThreadPool(poolSize);
    }

    @PreDestroy
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

}
