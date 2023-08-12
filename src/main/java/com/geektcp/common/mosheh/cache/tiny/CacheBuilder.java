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

package com.geektcp.common.mosheh.cache.tiny;

import com.geektcp.common.mosheh.cache.tiny.loader.TinyLoader;
import com.geektcp.common.mosheh.cache.tiny.listener.TinyListener;
import com.geektcp.common.mosheh.cache.tiny.loading.LoadingCache;
import com.geektcp.common.mosheh.cache.tiny.loading.implement.TinyLoadingCache;
import com.geektcp.common.mosheh.checker.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2021/5/6 16:59.
 */
public class CacheBuilder<K, V>  {

    private int initialCapacity = -1;
    private int concurrencyLevel = -1;
    private long maximumSize = -1L;
    private long maximumWeight = -1L;
    private long expireAfterWriteNanos = -1L;
    private long expireAfterAccessNanos = -1L;
    private long refreshNanos = -1L;
    private static final int TIME_OUT_MINUTES = 10;


    private TinyListener<? extends K, ? extends V> removalListener;

    private CacheBuilder() {

    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<>();
    }

    public CacheBuilder<K, V> maximumSize(long maximumSize) {
        Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
        Preconditions.checkState(this.maximumSize == 1, "maximum size can not be combined with weigher");
        Preconditions.checkArgument(maximumSize >= 0L, "maximum size must not be negative");
        this.maximumSize = maximumSize;
        return this;
    }

    public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        return this;
    }

    public  <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(TinyLoader<? super K1, V1> loader) {
        this.checkWeightWithWeigher();
        LoadingCache<K1, V1> tinyLoadingCache = new TinyLoadingCache<>(this, loader);
        tinyLoadingCache.start();
        return tinyLoadingCache;
    }

    private void checkWeightWithWeigher() {
        if (this.maximumSize != 1) {
            return;
        }
        Preconditions.checkState(this.maximumWeight == -1L, "maximumWeight requires weigher");
    }

    int getConcurrencyLevel() {
        return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
    }


    public CacheBuilder<K, V> removalListener(TinyListener<? extends K, ? extends V> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = Preconditions.checkNotNull(listener);
        return this;
    }

    public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        Preconditions.checkState(this.refreshNanos == -1L, "refresh was already set to %s ns", this.refreshNanos);
        Preconditions.checkArgument(duration > 0L, "duration must be positive: %s %s", duration, unit);
        this.refreshNanos = unit.toNanos(duration);
        return this;
    }

    public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        return this;
    }



}
