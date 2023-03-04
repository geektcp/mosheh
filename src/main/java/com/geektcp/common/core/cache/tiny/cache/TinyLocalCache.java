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
