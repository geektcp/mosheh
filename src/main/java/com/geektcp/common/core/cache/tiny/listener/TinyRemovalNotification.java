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

import com.geektcp.common.core.util.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.AbstractMap;

/**
 * @author geektcp on 2023/2/26 17:16.
 */
public class TinyRemovalNotification<K, V> extends AbstractMap.SimpleImmutableEntry<K, V> {

    private final TinyRemovalCause cause;
    private static final long serialVersionUID = 0L;

    public static <K, V> TinyRemovalNotification<K, V> create(@Nullable K key, @Nullable V value, TinyRemovalCause cause) {
        return new TinyRemovalNotification(key, value, cause);
    }

    private TinyRemovalNotification(@Nullable K key, @Nullable V value, TinyRemovalCause cause) {
        super(key, value);
        this.cause = (TinyRemovalCause) Preconditions.checkNotNull(cause);
    }

    public TinyRemovalCause getCause() {
        return this.cause;
    }

    public boolean wasEvicted() {
        return this.cause.wasEvicted();
    }

}
