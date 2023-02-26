package com.geektcp.common.core.cache.tiny;

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
