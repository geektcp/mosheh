package com.geektcp.common.mosheh.cache.tiny.storage;

/**
 * @author geektcp on 2023/7/21 16:12.
 */
public abstract class AbstractKey<K> implements Comparable<K> {

    public Long getId(){
        return null;
    }

    public Object getKey(){
        return null;
    }

    public int compareTo(K o){
        return 0;
    }

}
