package com.geektcp.common.mosheh.cache;

/**
 * @author geektcp on 2023/3/12 19:58.
 */
public interface Store<K, V> {

    boolean clear();

    boolean put(K nodeId, V v);

    boolean delete(K nodeId);

    int size();

    V getRoot();

    V fetch(K nodeId);

    V getHeight(K nodeId);

    V getHeight();

    void print();
}
