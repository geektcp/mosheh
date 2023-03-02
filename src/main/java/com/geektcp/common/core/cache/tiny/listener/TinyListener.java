package com.geektcp.common.core.cache.tiny.listener;


/**
 * @author geektcp on 2023/2/26 16:51.
 */
public interface TinyListener<K, V> {

    void onRemoval(TinyRemovalNotification<K, V> var1);

}
