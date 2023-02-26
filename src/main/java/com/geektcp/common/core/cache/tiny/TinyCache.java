package com.geektcp.common.core.cache.tiny;

import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CompatibleWith;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp  on 2021/5/6 16:59.
 */
public interface TinyCache<K, V> {

    V getIfPresent(@CompatibleWith("K") Object var1);

    V get(K var1, Callable<? extends V> var2) throws ExecutionException;

    ImmutableMap<K, V> getAllPresent(Iterable<?> var1);

    void put(K var1, V var2);

    void putAll(Map<? extends K, ? extends V> var1);

    void invalidate(@CompatibleWith("K") Object var1);

    void invalidateAll(Iterable<?> var1);

    void invalidateAll();

    long size();

    CacheStats stats();

    ConcurrentMap<K, V> asMap();

    void cleanUp();
}
