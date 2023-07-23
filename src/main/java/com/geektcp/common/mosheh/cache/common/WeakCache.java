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

package com.geektcp.common.mosheh.cache.common;

import com.geektcp.common.mosheh.cache.AbstractCache;
import com.geektcp.common.mosheh.cache.Cache;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geektcp on 2019/9/10.
 */
public final class WeakCache<K, V> extends AbstractCache<K, V> implements Cache<K, V> {

    private final int size;

    private final Map<K, V> eden;

    private final Map<K, V> longTerm;

    public WeakCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longTerm = new WeakHashMap<>(size);
    }

    @Override
    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            v = this.longTerm.get(k);
            if (v != null)
                this.eden.put(k, v);
        }
        return v;
    }

    public boolean put(K k, V v) {
        if (this.eden.size() >= size) {
            this.longTerm.putAll(this.eden);
            this.eden.clear();
        }
        this.eden.put(k, v);
        return true;
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
    public boolean delete(Object key) {

        return false;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
