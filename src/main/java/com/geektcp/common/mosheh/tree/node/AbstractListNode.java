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

package com.geektcp.common.mosheh.tree.node;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedList;
import java.util.List;

/**
 * @author geektcp on 2023/8/3 10:29.
 */
public class AbstractListNode<K extends Comparable> implements Node {

    protected K id;

    private K parentId;

    private boolean root;

    private List<AbstractListNode> children;

    public AbstractListNode() {
        this.id = null;
        this.parentId = null;
        this.root = false;
        this.children = new LinkedList<>();
    }

    /////////////////////////
    public final boolean isRoot() {
        return getRoot();
    }

    public final void setRoot(boolean root) {
        this.root = root;
    }

    public final boolean getRoot() {
        return this.root;
    }


    /////////////////////////
    public final boolean abstractAddChild(AbstractListNode childNode) {
        if (children == null) {
            children = new LinkedList<>();
        }
        return children.add(childNode);
    }

    public final void setId(K id) {
        this.id = id;
    }

    public final K getId() {
        return this.id;
    }

    public final void setParentId(K parentId) {
        this.parentId = parentId;
    }

    public final K getParentId() {
        return this.parentId;
    }

    public final List<AbstractListNode> getChildren() {
        return children;
    }

    public final void setChildren(List<AbstractListNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
    }
}
