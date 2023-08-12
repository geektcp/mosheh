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

package com.geektcp.common.mosheh.cache.tiny.storage.cache;

import com.geektcp.common.mosheh.cache.Store;
import com.geektcp.common.mosheh.cache.tiny.storage.key.AbstractKey;
import com.geektcp.common.mosheh.tree.RedBlackTree;

/**
 * @author geektcp on 2023/3/12 19:55.
 */
public class CacheTree<K extends AbstractKey<K>, V> extends RedBlackTree<K, V> implements Store<K, V> {

    public static boolean IS_VALID = true;

    public CacheTree() {
        // to do
        IS_VALID = true;
        this.init();

    }

    public CacheTree(K k, V v) {
        // to do

    }


    @Override
    public boolean clear() {
        return super.clear();
    }

    @Override
    public boolean put(K k, V v) {
        if(!IS_VALID){
            return false;
        }
        super.add(k, v);
        return true;
    }

    @Override
    public boolean delete(K nodeId) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V getRoot() {
        return null;
    }

    @Override
    public V fetch(K k) {
        return super.get(k);
    }

    @Override
    public V getHeight(K nodeId) {
        return null;
    }

    @Override
    public V getHeight() {
        return null;
    }

    public void print(){
        super.print();
    }

    public boolean invalid(){
        IS_VALID = false;
        return super.clear();
    }

    @Override
    public void start(){
        IS_VALID = true;
    }

    @Override
    public void stop(){
        IS_VALID = false;
        super.clear();
    }
}
