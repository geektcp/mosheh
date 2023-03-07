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

package com.geektcp.common.core.cache.tiny.listener;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 18:00.
 */
public interface ValueReference<K, V> {
    @Nullable
    V get();

    V waitForValue() throws ExecutionException;

    int getWeight();

    @Nullable
    TinyReferenceEntry<K, V> getEntry();

    ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @Nullable V var2, TinyReferenceEntry<K, V> var3);

    void notifyNewValue(@Nullable V var1);

    boolean isLoading();

    boolean isActive();
}
