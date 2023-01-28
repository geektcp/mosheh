package com.geektcp.common.core.tree.st;

/**
 * @author tanghaiyang on 2019/9/28.
 */
public interface UnorderedST<Key, Value> {

    int size();

    Value get(Key key);

    void put(Key key, Value value);

    void delete(Key key);
}
