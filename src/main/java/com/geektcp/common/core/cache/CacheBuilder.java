package com.geektcp.common.core.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

/**
 * @author tanghaiyang on 2021/5/6 16:59.
 */
public class CacheBuilder implements Cache{

    private static final int TIME_OUT_MINUTES = 10;

    public static <K, V> LoadingCache<K, V> create(CacheLoader<K, V> loader) {
        return create(loader, TIME_OUT_MINUTES);
    }

    public static <K, V> LoadingCache<K, V> create(CacheLoader loader, long durationMins) {
        return com.google.common.cache.CacheBuilder.newBuilder()
                .refreshAfterWrite(durationMins, TimeUnit.MINUTES)
                .build(loader);
    }

    public static <K, V> LoadingCache<K, V> create(CacheLoader loader, RemovalListener removalListener,
                                                   long durationMins) {
        return com.google.common.cache.CacheBuilder.newBuilder()
                .refreshAfterWrite(durationMins, TimeUnit.MINUTES)
                .removalListener(removalListener)
                .build(loader);
    }

    @Override
    public void clean() {

    }

    @Override
    public void refresh(String key) {

    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void delete(String key) {

    }
}
