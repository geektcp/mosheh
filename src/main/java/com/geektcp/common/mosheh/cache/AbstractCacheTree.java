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

package com.geektcp.common.mosheh.cache;

/**
 * @author geektcp on 2023/3/5 15:13.
 */
public abstract class AbstractCacheTree<K,V> implements Store<K,V> {

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public boolean put(K nodeId, V v) {
        return false;
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
    public V fetch(K nodeId) {
        return null;
    }

    @Override
    public V getHeight(K nodeId) {
        return null;
    }

    @Override
    public V getHeight() {
        return null;
    }

    @Override
    public void print() {

    }

    @Override
    public void travel() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


}
