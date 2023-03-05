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

package com.geektcp.common.core.cache.loading.implement;


import com.geektcp.common.core.cache.builder.TinyCacheBuilder;
import com.geektcp.common.core.cache.builder.loader.TinyLoader;
import com.geektcp.common.core.cache.loading.InvalidateCache;
import com.geektcp.common.core.cache.local.implement.SimpleCache;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * @author geektcp on 2023/2/26 18:28.
 */
public class TinyLoadingCache<K, V>  implements InvalidateCache<K, V> {
    private static final long serialVersionUID = 1L;

    private SimpleCache localCache = new SimpleCache();
    private TinyCacheBuilder builder;
    private TinyLoader cacheLoader;

    private Map<Object, Object> cacheMap = new HashMap<>();
    private Map<Object, Long> expireTimeMap = new HashMap<>();

    public TinyLoadingCache() {

    }

    public TinyLoadingCache(TinyLoader<? super K, V> loader) {
        this.cacheLoader = loader;
    }

    public TinyLoadingCache(TinyCacheBuilder<? super K, ? super V> builder, TinyLoader<? super K, V> loader) {
        this.builder = builder;
        this.cacheLoader = loader;
    }

    public Object get(Object k) {
        return  cacheMap.get(k);
    }

    @Override
    public boolean put(K k, V v) {
        cacheMap.put(k,v);
        return true;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public boolean refresh(Object key) {
        return false;
    }

    @Override
    public boolean delete(Object key) {

        return false;
    }

    public Object getUnchecked(Object key) {
        try {
            return this.get(key);
        } catch (Exception var3) {
            throw new RuntimeException(var3.getCause());
        }
    }

    public Map<K, V> getAll(Iterable<? extends K> keys) {
        return null;
    }

    @Nullable
    @Override
    public V getIfPresent(Object o) {
        return null;
    }

    @Override
    public V get(K k, Callable<? extends V> callable) {
        return null;
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



}
