/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geektcp.common.mosheh.cache.tiny.storage.key;


import com.geektcp.common.mosheh.generator.IdGenerator;
import com.geektcp.common.mosheh.system.Sys;

import java.util.Objects;

/**
 * @author geektcp on 2023/3/12 20:18.
 */
public class NodeKey<K> extends AbstractKey<K> {

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
        if (Objects.isNull(currentKey)) {
            return -2000;
        }

        try {
            NodeKey nodeKey = (NodeKey) k;
            if(this.currentKey.equals(nodeKey.getKey())){
                return 0;
            }
        }catch (Exception e){
            Sys.p("k is not NodeKey type!");
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
