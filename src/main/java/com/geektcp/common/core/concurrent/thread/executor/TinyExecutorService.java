package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.system.Sys;

import java.util.Objects;
import java.util.concurrent.*;

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

    public void init() {
        tinyExecutor = TinyExecutorBuilder.newFixedThreadPool(poolSize);
    }

    public static TinyExecutorService getInstance() {
        if (Objects.isNull(instance)) {
            return new TinyExecutorService();
        }
        return instance;
    }

    public ExecutorService getTinyExecutor() {
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
