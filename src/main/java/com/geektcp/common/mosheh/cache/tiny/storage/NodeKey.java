package com.geektcp.common.mosheh.cache.tiny.storage;


import com.geektcp.common.mosheh.generator.IdGenerator;

import java.util.Objects;

/**
 * @author geektcp on 2023/3/12 20:18.
 */
public class NodeKey<K> extends AbstractKey<K> implements Comparable<K> {

    private Long id;

    private K currentKey;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Object getKey() {
        return this.currentKey;
    }

    public NodeKey(K k) {
        this.id = IdGenerator.getId();
        this.currentKey = k;
    }

    public NodeKey build(K k) {
        return new NodeKey<>(k);
    }


    @Override
    public int compareTo(K k) {
        if (Objects.isNull(k)) {
            return -1000;
        }
        NodeKey nodeKey = (NodeKey) k;
//        if (this.id > nodeKey.getId()) {
//            return 1;
//        }
//        if (this.id < nodeKey.getId()) {
//            return -1;
//        }

        if(this.currentKey.equals(nodeKey.getKey())){
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return Objects.hash(currentKey);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id)
                .append("|")
                .append("currentKey: ").append(currentKey);
        return sb.toString();
    }

}
