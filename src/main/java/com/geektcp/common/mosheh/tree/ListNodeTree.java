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

package com.geektcp.common.mosheh.tree;

import com.geektcp.common.mosheh.tree.node.AbstractListNode;
import com.geektcp.common.mosheh.util.CollectionUtils;
import com.geektcp.common.mosheh.util.ObjectUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author geektcp on 2023/8/2 23:25.
 */
@SuppressWarnings("unchecked")
public class ListNodeTree {

    /**
     * @param list  dataSource
     * @param clazz return Object class
     * @param <T>   node type
     * @return one tree
     * clazz.newInstance() is deprecated by jdk 11~17 version
     */
    public static <T extends AbstractListNode> T createTree(List<T> list, Class<T> clazz)
            throws
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        T currentNode = clazz.getDeclaredConstructor().newInstance();
        currentNode.setRoot(true);
        List<T> listCopy = (List<T>) ObjectUtils.deepCopy(list);
        if (Objects.nonNull(listCopy)) {
            listCopy.forEach(childNode -> {
                insertNode(currentNode, childNode);
            });
        }
        return currentNode;
    }

    public static <T extends AbstractListNode> List createTreeList(List<T> list, Class<T> clazz)
            throws
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        return createTree(list, clazz).getChildrenNodeList();
    }

    public static <T extends AbstractListNode> List parseTreeList(List<T> treeList) {
        if (CollectionUtils.isEmpty(treeList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        treeList.forEach(node -> {
            resultList.add(node);
            List<T> childrenNode = parseTreeList(node.getChildrenNodeList());
            if (CollectionUtils.isNotEmpty(childrenNode)) {
                resultList.addAll(childrenNode);
            }
        });
        return resultList;
    }


    ////////////////////////////////////////////////////////////////////////
    /**
     * @param currentNode node
     * @param childNode   node
     * @param <T>         node type
     * @return true:success, false: failed
     */
    private static <T extends AbstractListNode> boolean insertNode(T currentNode, T childNode) {
        Comparable currentId = currentNode.getNodeId();
        Comparable childParentId = childNode.getParentNodeId();

        // add the node which parentNode is root node
        if (currentNode.isRoot() && Objects.isNull(childParentId)) {
            childNode.setParentNode(currentNode.getNode());
            currentNode.abstractAddChild(childNode);
            return true;
        }

        // add normal child node
        if (Objects.nonNull(currentId) && currentId.equals(childParentId)) {
            childNode.setParentNode(currentNode.getNode());
            currentNode.abstractAddChild(childNode);
            return true;
        }

        // other wise, recursive call the insertNode
        if (Objects.nonNull(currentNode.getChildrenNodeList())) {
            currentNode.getChildrenNodeList().forEach(currentChildNode -> {
                insertNode((T) currentChildNode, childNode);
            });
        }

        // nodes without parent-child relationships
        return false;
    }

}
