/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geektcp.common.core.cache.tiny.cache;


import com.geektcp.common.core.cache.tiny.TinyCacheBuilder;
import com.geektcp.common.core.cache.tiny.loader.TinyCacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 18:28.
 */
public class TinyLocalLoadingCache<K, V>  implements TinyLoadingCache<K, V> {
    private static final long serialVersionUID = 1L;

    private final TinyLocalCache<K, V> localCache = new TinyLocalCache();
    private TinyCacheBuilder builder;
    private TinyCacheLoader cacheLoader;

    public TinyLocalLoadingCache() {

    }

    TinyLocalLoadingCache(TinyCacheBuilder<? super K, ? super V> builder, TinyCacheLoader<? super K, V> loader) {
        this.builder = builder;
        this.cacheLoader = loader;
    }

    public V get(K key) {
//        return this.localCache.getOrLoad(key);
        return null;
    }

    public V getUnchecked(K key) {
        try {
            return this.get(key);
        } catch (Exception var3) {
            throw new RuntimeException(var3.getCause());
        }
    }

    public Map<K, V> getAll(Iterable<? extends K> keys) {
//        return this.localCache.getAll(keys);
        return null;
    }

    public void refresh(K key) {
        this.localCache.refresh(key);
    }

    public final V apply(K key) {
        return this.getUnchecked(key);
    }


    @Override
    public ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Nullable
    @Override
    public V getIfPresent(Object o) {
        return null;
    }

    @Override
    public V get(K k, Callable<? extends V> callable) throws ExecutionException {
        return null;
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        return null;
    }

    @Override
    public void put(K k, V v) {

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void invalidate(Object o) {

    }

    @Override
    public void invalidateAll(Iterable<?> iterable) {

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
    public void cleanUp() {

    }
}
