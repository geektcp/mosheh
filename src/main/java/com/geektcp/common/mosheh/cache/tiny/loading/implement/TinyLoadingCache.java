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

package com.geektcp.common.mosheh.cache.tiny.loading.implement;


import com.geektcp.common.mosheh.cache.tiny.CacheBuilder;
import com.geektcp.common.mosheh.cache.tiny.loader.TinyLoader;
import com.geektcp.common.mosheh.cache.tiny.loading.LoadingCache;
import com.geektcp.common.mosheh.cache.tiny.local.TinyCache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author geektcp on 2023/2/26 18:28.
 */
public class TinyLoadingCache<K, V> implements LoadingCache<K, V> ,Serializable {

    private TinyCache<K, V> tinyCache;

    private CacheBuilder builder;
    private TinyLoader<? super K, V> loader;

    private Map<K, Long> expireTimeMap = new HashMap<>();

    public TinyLoadingCache(TinyCache<K, V> tinyCache) {
        this.tinyCache = tinyCache;
    }

    public TinyLoadingCache(TinyCache<K, V> tinyCache, TinyLoader<K, V> loader) {
        this.tinyCache = tinyCache;
        this.loader = loader;
    }

    public TinyLoadingCache(TinyLoader<K, V> loader) {
        this.loader = loader;
        this.tinyCache = new TinyCache<>();
    }

    public TinyLoadingCache(CacheBuilder<? super K, ? super V> builder, TinyLoader<? super K, V> loader) {
        this.builder = builder;
        this.loader = loader;
        this.tinyCache = new TinyCache<>();
    }

    @Override
    public boolean clear() {
        return tinyCache.clear();
    }

    @Override
    public boolean delete(K k) {
        return tinyCache.delete(k);
    }

    @Override
    public V get(K k) {
        return tinyCache.get(k);
    }

    @Override
    public boolean put(K k, V v) {
        return tinyCache.put(k, v);
    }

    @Override
    public V getUnchecked(K k) {
        return null;
    }

    @Override
    public Map<K, V> getAll(Iterable<? extends K> var1) {
        return null;
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Override
    public boolean refresh(K key) {
        return false;
    }

    @Override
    public void print(){
        tinyCache.print();
    }

    @Override
    public void travel(){
        tinyCache.travel();
    }

}
