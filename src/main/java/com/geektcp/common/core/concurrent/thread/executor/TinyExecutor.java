package com.geektcp.common.core.concurrent.thread.executor;


import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author geektcp on 2023/2/18 23:18.
 */
public class TinyExecutor extends AbstractExecutorService {

    private final ExecutorService e;

    TinyExecutor(ExecutorService executor) {
        if (Objects.isNull(executor)) {
            e = TinyExecutorBuilder.newSingleThreadExecutor();
            return;
        }
        e = executor;
    }

    public void execute(Runnable command) {
        e.execute(command);
    }

    public void shutdown() {
        e.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return e.shutdownNow();
    }

    public boolean isShutdown() {
        return e.isShutdown();
    }

    public boolean isTerminated() {
        return e.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return e.awaitTermination(timeout, unit);
    }

    public Future<?> submit(Runnable task) {
        return e.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return e.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return e.submit(task, result);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return e.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                         long timeout, TimeUnit unit)
            throws InterruptedException {
        return e.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny( Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return e.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                           long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return e.invokeAny(tasks, timeout, unit);
    }
}
