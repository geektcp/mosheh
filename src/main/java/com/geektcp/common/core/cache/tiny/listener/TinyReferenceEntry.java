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

/**
 * @author geektcp on 2023/2/26 17:55.
 */
public interface TinyReferenceEntry <K, V> {

    ValueReference<K, V> getValueReference();

    void setValueReference(ValueReference<K, V> var1);

    @Nullable
    TinyReferenceEntry<K, V> getNext();

    int getHash();

    @Nullable
    K getKey();

    long getAccessTime();

    void setAccessTime(long var1);

    TinyReferenceEntry<K, V> getNextInAccessQueue();

    void setNextInAccessQueue(TinyReferenceEntry<K, V> var1);

    TinyReferenceEntry<K, V> getPreviousInAccessQueue();

    void setPreviousInAccessQueue(TinyReferenceEntry<K, V> var1);

    long getWriteTime();

    void setWriteTime(long var1);

    TinyReferenceEntry<K, V> getNextInWriteQueue();

    void setNextInWriteQueue(TinyReferenceEntry<K, V> var1);

    TinyReferenceEntry<K, V> getPreviousInWriteQueue();

    void setPreviousInWriteQueue(TinyReferenceEntry<K, V> var1);
}
