package com.geektcp.common.core.cache.tiny.storage;


import java.util.Objects;

/**
 * @author geektcp on 2023/3/12 20:18.
 */
public class NodeKey<K> implements Comparable {

    private Long id;

    private K currentKey;



    public Long getId() {
        return this.id;
    }


    public NodeKey(K k){
        this.currentKey = k;
    }

    public NodeKey build(K k){
        return new NodeKey<>(k);
    }

    @Override
    public int compareTo(Object o) {
        if(Objects.isNull(o)){
            return -1000;
        }
        NodeKey nodeKey = (NodeKey)o;
        if( this.id > nodeKey.getId()){
            return 1;
        }
        if( this.id < nodeKey.getId()){
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return Objects.hash(currentKey);
    }


}
