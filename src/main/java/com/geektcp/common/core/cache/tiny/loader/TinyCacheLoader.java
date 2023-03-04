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

import com.geektcp.common.core.util.Preconditions;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author geektcp on 2023/2/26 17:30.
 */
public abstract class TinyCacheLoader<K, V> {

    protected TinyCacheLoader() {
    }

    public abstract V load(K var1) throws Exception;

    public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
        throw new RuntimeException();
    }

    public static <K, V> TinyCacheLoader<K, V> asyncReloading(final TinyCacheLoader<K, V> loader, final Executor executor) {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(executor);
        return new TinyCacheLoader<K, V>() {
            public V load(K key) throws Exception {
                return loader.load(key);
            }

            public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
                return loader.loadAll(keys);
            }
        };
    }

    public static final class InvalidCacheLoadException extends RuntimeException {
        public InvalidCacheLoadException(String message) {
            super(message);
        }
    }

    public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
        UnsupportedLoadingOperationException() {
        }
    }


}
