package com.geektcp.common.mosheh.cache.tiny.storage;

/**
 * @author geektcp on 2023/3/12 20:47.
 */
public class Key {
    private Long id;
    private Object localKey;

    public Key(Object key) {
        this.localKey = key;
    }


    public Long getId() {
        return this.id;
    }

    public Object getLocalKey() {
        return this.localKey;
    }

    public Key build(Object key) {
        return new Key(key);
    }


}
