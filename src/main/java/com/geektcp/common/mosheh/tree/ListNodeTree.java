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
import java.util.*;

/**
 * @author geektcp on 2023/8/2 23:25.
 */
@SuppressWarnings("unchecked")
public class ListNodeTree {

    public static <T extends AbstractListNode> T createTree(List<T> list, Class<T> clazz)
            throws IllegalAccessException, InstantiationException{
        T currentNode = clazz.newInstance();
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
            throws IllegalAccessException, InstantiationException {
        return createTree(list, clazz).getChildren();
    }

    public static <T extends AbstractListNode> List parseTreeList(List<T> treeList){
        if (CollectionUtils.isEmpty(treeList)){
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        treeList.forEach(node -> {
            resultList.add(node);
            List<T> childrenNode = parseTreeList(node.getChildren());
            if (CollectionUtils.isNotEmpty(childrenNode)){
                resultList.addAll(childrenNode);
            }
        });
        return resultList;
    }


    ////////////////////////////////////


    private static <T extends AbstractListNode> boolean insertNode(T currentNode, T childNode) {
        Comparable currentId = currentNode.getId();
        Comparable childParentId = childNode.getParentId();

        if(currentNode.isRoot() && Objects.isNull(childParentId)){
            currentNode.abstractAddChild(childNode);
            return true;
        }

        if( Objects.nonNull(currentId) && currentId.equals(childParentId) ){
            currentNode.abstractAddChild(childNode);
            return true;
        }
        if (Objects.nonNull(currentNode.getChildren())){
            currentNode.getChildren().forEach(currentChildNode ->{
                insertNode((T)currentChildNode, childNode);
            });
        }

        return false;
    }

}
