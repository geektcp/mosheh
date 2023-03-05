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

package com.geektcp.common.core.cache.local.implement;

import com.geektcp.common.core.cache.loading.InvalidateCache;
import com.geektcp.common.core.cache.local.AbstractCache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 17:50.
 */
public class TinyCache<K, V> extends AbstractCache<K, V> implements InvalidateCache<K, V> {


    public V putIfAbsent(K key, V value){
        return null;
    }

    public TinyCache() {
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
    public void putAll(Map<? extends K, ? extends V> var1) {
        // do noting

    }

    @Override
    public void invalidate(Object var1) {
        // do noting

    }

    @Override
    public void invalidateAll(Iterable<?> var1) {
        // do noting

    }

    @Override
    public void invalidateAll() {
        // do noting

    }

    @Override
    public V getUnchecked(K var1) {
        return null;
    }

    @Override
    public Map<K, V> getAll(Iterable<? extends K> var1) throws ExecutionException {
        return null;
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public boolean refresh(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean put(Object key, Object value) {
        return false;
    }

    @Override
    public boolean delete(Object key) {

        return false;
    }
}
