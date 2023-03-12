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

package com.geektcp.common.core.cache.tiny.loader;

import com.geektcp.common.core.concurrent.thread.executor.service.TinyExecutor;
import com.geektcp.common.core.exception.BaseException;
import com.geektcp.common.core.cheker.Preconditions;

import java.util.Map;


/**
 * @author geektcp on 2023/2/26 17:30.
 */
public abstract class TinyLoader<K, V> implements CacheLoader {

    protected TinyLoader() {
    }

    /**
     * generate value
     *
     * @param k key
     * @return value
     */
    public abstract V load(K k);

    public Map<K, V> loadAll(Iterable<? extends K> keys) {
        throw new BaseException();
    }

    /*
    public ListenableFuture<V> reload(K key, V oldValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(oldValue);
        return Futures.immediateFuture(load(key));
    }

    public static <K, V> TinyLoader<K, V> asyncReloading(
            final TinyLoader<K, V> loader, final TinyExecutor executor) {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(executor);
        return new TinyLoader<K, V>() {
            @Override
            public V load(K key) {
                return loader.load(key);
            }

            @Override
            public ListenableFuture<V> reload(final K key, final V oldValue) {
                ListenableFutureTask<V> task =
                        ListenableFutureTask.create(() -> loader.reload(key, oldValue).get());
                executor.execute(task);
                return task;
            }

            @Override
            public Map<K, V> loadAll(Iterable<? extends K> keys) {
                return loader.loadAll(keys);
            }
        };
    }
    */

}
