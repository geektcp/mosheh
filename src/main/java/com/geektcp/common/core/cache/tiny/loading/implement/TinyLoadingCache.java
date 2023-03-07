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

package com.geektcp.common.core.cache.tiny.loading.implement;


import com.geektcp.common.core.cache.tiny.CacheBuilder;
import com.geektcp.common.core.cache.tiny.loader.TinyLoader;
import com.geektcp.common.core.cache.tiny.loading.InvalidateCache;
import com.geektcp.common.core.cache.tiny.local.TinyCache;
import com.geektcp.common.core.exception.BaseException;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * @author geektcp on 2023/2/26 18:28.
 */
public class TinyLoadingCache<K, V>  implements InvalidateCache<K, V> {

    private CacheBuilder builder;
    private TinyLoader<K, V> loader;

    private TinyCache<K, V> tinyCache = new TinyCache<>();
    private Map<K, Long> expireTimeMap = new HashMap<>();


    public TinyLoadingCache() {

    }

    public TinyLoadingCache(TinyLoader<K, V> loader) {
        this.loader = loader;
    }

    public TinyLoadingCache(CacheBuilder<? super K, ? super V> builder, TinyLoader<K, V> loader) {
        this.builder = builder;
        this.loader = loader;
    }

    public V get(K k) {
        return tinyCache.get(k);
    }

    @Override
    public boolean put(K k, V v) {
        return tinyCache.put(k,v);
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
    public boolean refresh(K k) {
        return false;
    }

    @Override
    public boolean delete(K k) {
        return false;
    }

    public V getUnchecked(K key) {
        try {
            return this.get(key);
        } catch (Exception e) {
            throw new BaseException(e);
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
        // do noting

    }

    @Override
    public void invalidate(Object o) {
        // do noting

    }

    @Override
    public void invalidateAll(Iterable<?> iterable) {
        // do noting

    }

    @Override
    public void invalidateAll() {
        // do noting

    }



}
