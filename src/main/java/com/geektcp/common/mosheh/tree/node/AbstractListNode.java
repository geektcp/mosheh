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
 * <p>
 * These names are used to prevent conflicts with existing user class:
 * nodeId
 * nodeName
 * parentNodeId
 * parentNodeName
 * childrenNodeList
 * rootNode
 * <p>
 * so mosheh do not use these names:
 * id, parentId, name, parentName
 *
 */
public class AbstractListNode<K extends Comparable> implements Node {

    protected K nodeId;

    private Object node;

    private K parentNodeId;

    private Object parentNode;

    private boolean root;

    private List<AbstractListNode> childrenNodeList;

    public AbstractListNode() {
        this.nodeId = null;
        this.parentNodeId = null;
        this.root = false;
        this.childrenNodeList = new LinkedList<>();
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
        if (childrenNodeList == null) {
            childrenNodeList = new LinkedList<>();
        }
        return childrenNodeList.add(childNode);
    }

    public final void setNodeId(K nodeId) {
        this.nodeId = nodeId;
    }

    public final K getNodeId() {
        return this.nodeId;
    }


    public final void setNodeName(Object node) {
        this.node = node;
    }

    public final Object getNode() {
        return this.node;
    }

    public final void setParentNodeId(K parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public final K getParentNodeId() {
        return this.parentNodeId;
    }

    public final void setParentNode(Object parentNode) {
        this.parentNode = parentNode;
    }

    public final Object getParentNode() {
        return this.parentNode;
    }

    public final List<AbstractListNode> getChildrenNodeList() {
        return childrenNodeList;
    }

    public final void setChildrenNodeList(List<AbstractListNode> childrenNodeList) {
        this.childrenNodeList = childrenNodeList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
    }
}
