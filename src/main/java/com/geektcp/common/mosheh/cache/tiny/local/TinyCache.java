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

package com.geektcp.common.mosheh.cache.tiny.local;

import com.geektcp.common.mosheh.cache.Store;
import com.geektcp.common.mosheh.cache.tiny.CacheBuilder;
import com.geektcp.common.mosheh.cache.tiny.loader.TinyLoader;
import com.geektcp.common.mosheh.cache.AbstractCache;
import com.geektcp.common.mosheh.cache.tiny.storage.cache.TreeCacheFactory;
import com.geektcp.common.mosheh.cache.tiny.storage.key.NodeKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author geektcp on 2023/2/26 17:50.
 */
public class TinyCache<K , V> extends AbstractCache<K, V> {

    private Store<NodeKey, V> store;

    private Map<K, Long> expireTimeMap = new HashMap<>();

    public V putIfAbsent(K key, V value) {
        return null;
    }


    public TinyCache() {
        // build complex cache
        this.store = TreeCacheFactory.build().buildCache();
    }

    public TinyCache(CacheBuilder<? super K, ? super V> builder, TinyLoader<? super K, V> loader) {


    }

    @Override
    public boolean clear() {
        return store.clear();
    }

    @Override
    public boolean refresh(K k) {
        return false;
    }

    @Override
    public V get(K k) {
        if(Objects.isNull(k)){
            return null;
        }
        return store.fetch(new NodeKey<>(k));
    }

    @Override
    public boolean put(K k, V v) {
        if(Objects.isNull(k)){
            return false;
        }
        store.put(new NodeKey<>(k), v);
        return true;
    }

    @Override
    public boolean delete(K k) {
        return store.delete(new NodeKey<>(k));
    }

    public void print(){
        store.print();
    }
    @Override
    public void travel(){
        store.travel();
    }

    @Override
    public void start(){
        store.start();
    }

    @Override
    public void stop(){
        store.stop();
    }
}
