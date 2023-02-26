package com.geektcp.common.core.cache.tiny;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/**
 * @author geektcp on 2023/2/26 17:30.
 */
public abstract class TinyCacheLoader<K, V> {

    protected TinyCacheLoader() {
    }

    public abstract V load(K var1) throws Exception;

    @GwtIncompatible
    public ListenableFuture<V> reload(K key, V oldValue) throws Exception {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(oldValue);
        return Futures.immediateFuture(this.load(key));
    }

    public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
        throw new RuntimeException();
    }

    public static <K, V> TinyCacheLoader<K, V> from(Function<K, V> function) {
        return new TinyCacheLoader.FunctionToCacheLoader(function);
    }

    public static <V> TinyCacheLoader<Object, V> from(Supplier<V> supplier) {
        return new TinyCacheLoader.SupplierToCacheLoader(supplier);
    }

    @GwtIncompatible
    public static <K, V> TinyCacheLoader<K, V> asyncReloading(final CacheLoader<K, V> loader, final Executor executor) {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(executor);
        return new TinyCacheLoader<K, V>() {
            public V load(K key) throws Exception {
                return loader.load(key);
            }

            public ListenableFuture<V> reload(final K key, final V oldValue) throws Exception {
                ListenableFutureTask<V> task = ListenableFutureTask.create(new Callable<V>() {
                    public V call() throws Exception {
                        return loader.reload(key, oldValue).get();
                    }
                });
                executor.execute(task);
                return task;
            }

            public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
                return loader.loadAll(keys);
            }
        };
    }

    public static final class InvalidCacheLoadException extends RuntimeException {
        public InvalidCacheLoadException(String message) {
            super(message);
        }
    }

    public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
        UnsupportedLoadingOperationException() {
        }
    }

    private static final class SupplierToCacheLoader<V> extends TinyCacheLoader<Object, V> implements Serializable {
        private final Supplier<V> computingSupplier;
        private static final long serialVersionUID = 0L;

        public SupplierToCacheLoader(Supplier<V> computingSupplier) {
            this.computingSupplier = (Supplier)Preconditions.checkNotNull(computingSupplier);
        }

        public V load(Object key) {
            Preconditions.checkNotNull(key);
            return this.computingSupplier.get();
        }
    }

    private static final class FunctionToCacheLoader<K, V> extends TinyCacheLoader<K, V> implements Serializable {
        private final Function<K, V> computingFunction;
        private static final long serialVersionUID = 0L;

        public FunctionToCacheLoader(Function<K, V> computingFunction) {
            this.computingFunction = (Function)Preconditions.checkNotNull(computingFunction);
        }

        public V load(K key) {
            return this.computingFunction.apply(Preconditions.checkNotNull(key));
        }
    }
}
