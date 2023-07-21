package com.geektcp.common.mosheh.cache.tiny.storage;

/**
 * @author geektcp on 2023/3/12 20:47.
 */
public class LocalKey<K> extends AbstractKey<K> {
    private Long id;
    private Object key;

    public LocalKey(Object key) {
        this.key = key;
    }


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Object getKey() {
        return this.key;
    }

    public LocalKey build(Object key) {
        return new LocalKey(key);
    }


    public int compareTo(K k){
        return 0;
    }

}
