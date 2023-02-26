package com.geektcp.common.core.cache.tiny;

import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 17:50.
 */
public class TinyLocalCache<K, V> implements TinyCache<K, V> {


    public V putIfAbsent(K key, V value){
        return null;
    }

    protected TinyLocalCache() {
        super();
    }


    @Override
    public V getIfPresent(Object var1) {
        return null;
    }

    @Override
    public V get(K var1, Callable<? extends V> var2) throws ExecutionException {
        return null;
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> var1) {
        return null;
    }

    @Override
    public void put(K var1, V var2) {

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> var1) {

    }

    @Override
    public void invalidate(Object var1) {

    }

    @Override
    public void invalidateAll(Iterable<?> var1) {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public CacheStats stats() {
        return null;
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Override
    public void cleanUp() {

    }


    public void refresh(K key) {

    }
}
