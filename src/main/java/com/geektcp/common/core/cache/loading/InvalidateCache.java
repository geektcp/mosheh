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

package com.geektcp.common.core.cache.loading;


import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp  on 2021/5/6 16:59.
 */
public interface InvalidateCache<K, V> extends LoadingCache<K, V> {

    V getIfPresent( Object var1);

    V get(K var1, Callable<? extends V> var2) throws ExecutionException;

    void putAll(Map<? extends K, ? extends V> var1);

    void invalidate(Object var1);

    void invalidateAll(Iterable<?> var1);

    void invalidateAll();

    long size();

}
