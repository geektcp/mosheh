package com.geektcp.common.core.cache.tiny;

import com.google.common.base.Function;
import com.google.common.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 17:26.
 */
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V> {
    V get(K var1) throws ExecutionException;

    V getUnchecked(K var1);

    Map<K, V> getAll(Iterable<? extends K> var1) throws ExecutionException;

    /** @deprecated */
    @Deprecated
    V apply(K var1);

    void refresh(K var1);

    ConcurrentMap<K, V> asMap();
}
