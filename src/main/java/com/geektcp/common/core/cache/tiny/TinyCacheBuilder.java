package com.geektcp.common.core.cache.tiny;

import com.geektcp.common.core.util.Preconditions;

import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2021/5/6 16:59.
 */
public class TinyCacheBuilder<K, V>  {

    int initialCapacity = -1;
    int concurrencyLevel = -1;
    long maximumSize = -1L;
    long maximumWeight = -1L;
    long expireAfterWriteNanos = -1L;
    long expireAfterAccessNanos = -1L;
    long refreshNanos = -1L;
    private static final int TIME_OUT_MINUTES = 10;


    TinyListener<? super K, ? super V> removalListener;


    private TinyCacheBuilder() {

    }

    public static TinyCacheBuilder<Object, Object> newBuilder() {
        return new TinyCacheBuilder();
    }

    public TinyCacheBuilder<K, V> maximumSize(long maximumSize) {
        Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
        Preconditions.checkState(this.maximumSize == 1, "maximum size can not be combined with weigher");
        Preconditions.checkArgument(maximumSize >= 0L, "maximum size must not be negative");
        this.maximumSize = maximumSize;
        return this;
    }

    public TinyCacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        return this;
    }

    public <K1 extends K, V1 extends V> TinyLoadingCache<K1, V1> build(TinyCacheLoader<? super K1, V1> loader) {
        this.checkWeightWithWeigher();
        return new TinyLocalLoadingCache();
    }

    private void checkWeightWithWeigher() {
        if (this.maximumSize == 1) {
            Preconditions.checkState(this.maximumWeight == -1L, "maximumWeight requires weigher");
        }
// else if (this.strictParsing) {
//            Preconditions.checkState(this.maximumWeight != -1L, "weigher requires maximumWeight");
//        } else if (this.maximumWeight == -1L) {
//            logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
//        }

    }

    int getConcurrencyLevel() {
        return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
    }


    public <K1 extends K, V1 extends V> TinyCacheBuilder<K, V> removalListener(TinyListener<? super K1, ? super V1> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (TinyListener)Preconditions.checkNotNull(listener);
        return this;
    }

    public TinyCacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        Preconditions.checkState(this.refreshNanos == -1L, "refresh was already set to %s ns", this.refreshNanos);
        Preconditions.checkArgument(duration > 0L, "duration must be positive: %s %s", duration, unit);
        this.refreshNanos = unit.toNanos(duration);
        return this;
    }

    public TinyCacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        return this;
    }



}
