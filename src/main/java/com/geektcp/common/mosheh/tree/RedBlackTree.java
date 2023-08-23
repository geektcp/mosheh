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


import com.alibaba.fastjson.JSON;
import com.geektcp.common.mosheh.cache.AbstractCacheTree;
import com.geektcp.common.mosheh.cache.tiny.storage.key.AbstractKey;
import com.geektcp.common.mosheh.system.Sys;
import com.geektcp.common.mosheh.tree.node.AbstractBinaryNode;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * @author geektcp on 2019/9/16.
 */
public class RedBlackTree<K extends AbstractKey<K>, V> extends AbstractCacheTree<K,V> {

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    private static final String NODE_LEFT = "left";
    private static final String NODE_RIGHT = "right";


    private class Node extends AbstractBinaryNode {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private boolean color;

        Node() {
            // init
        }

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = RED;
        }

        public boolean isEmpty() {
            return Objects.isNull(key);
        }

        public boolean isRoot() {
            return this.root;
        }

        public void setColor(boolean color){
            this.color = color;
        }

        public boolean getColor(){
            return this.color;
        }

        public void setKey(K key){
            this.key = key;
        }

        public K getKey(){
            return this.key;
        }

        public void setValue(V value){
            this.value = value;
        }

        public V getValue(){
            return this.value;
        }

        public boolean clear() {
            this.setColor(false);
            this.setKey(null);
            this.setValue(null);
            return true;
        }


        public void print() {
            Sys.p("key: {} | value: {}", key.toString(), value);
        }


        /////////////////////
        private void init(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private WeakReference<Map<Object, Object>> weakMap;

    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    protected void init() {
        this.root = new Node();
        this.weakMap = new WeakReference<>(new HashMap<>());
    }


    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " not exist！");
        }

        node.value = newValue;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
//        List<List<Node>> nodes = new ArrayList<>();
//        List<Node> nodePair = new ArrayList<>();
//        nodePair.add(root);
//        nodes.add(nodePair);
//        print(nodes);
        print(root);
    }

    /**
     * @param key any key
     * @return value
     */
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }

        return null;
    }


    /**
     * main add method
     *
     * @param key   any Key
     * @param value any Value
     */
    public void add(K key, V value) {
        add(root, key, value);

        root.color = BLACK;
    }

    /**
     * travel the all node
     * then load it into map and u can print it
     */
    public void travel() {
        Map<Object, Object> map = weakMap.get();
        travel(root, map);
        Sys.p(JSON.toJSONString(map, true));
    }

    public boolean clear() {
        try {
            clear(root);
            clear(weakMap);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    /**
     * @param node any node
     * @return boolean color
     */
    private boolean isRed(Node node) {
        if (Objects.isNull(node)) {
            return BLACK;
        }
        return node.color;
    }


    /**
     *  left rotate, look at x
     *
     *        node                                   x
     *       /    \              left             /    \
     *      T1     x           --------->       node    T3
     *     /   \                              /    \
     *    T2    T3                           T1    T2
     *
     * @param node any node
     * @return node
     */
    private Node leftRotate(Node node) {
        Node x = node.right;

        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;

        return x;
    }


    /**
     *  right rotate, look at x
     *
     *        node                                    x
     *       /    \              right              /   \
     *      x     T2           --------->         y     node
     *     /  \                                        /    \
     *    y   T1                                      T1    T2
     *
     * @param node any node
     * @return node
     */
    private Node rightRotate(Node node) {
        Node x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private void add(Node node, K key, V value) {
        if(Objects.isNull(node)){
            return;
        }
        if (node.isEmpty()) {
            size++;
            node.init(key, value);
            return;
        }

//        int compare = getCompare(node, key);
//        if (compare < 0) {
//            node.left = add(node.left, key, value);
//        } else if (compare > 0) {
//            node.right = add(node.right, key, value);
//        } else {
//            // key.compareTo(node.key) == 0
//            node.value = value;
//        }
//        return rotate(node);

        if (Objects.isNull(node.left)) {
            node.left = new Node();
            add(node.left, key, value);
            return;
        }
        if (Objects.isNull(node.right)) {
            node.right = new Node();
            add(node.right, key, value);
            return;
        }

        add(node.left, key, value);
    }

    private void travel(Node node, Map<Object, Object> map) {
        if (Objects.isNull(node) || node.isEmpty()) {
            return;
        }
        Map<Object, Object> data = new HashMap<>();
        map.put(node.key.getKey(), data);

        if (Objects.nonNull(node.left)) {
            Map<Object, Object> left = new HashMap<>();
            data.put(NODE_LEFT, left);
            travel(node.left, left);
        }

        if (Objects.nonNull(node.right)) {
            Map<Object, Object> right = new HashMap<>();
            data.put(NODE_RIGHT, right);
            travel(node.right, right);
        }

    }

    /**
     * clean all node data
     * @param node the target which will be clear
     */
    private void clear(Node node) {
        if (Objects.isNull(node) ) {
            return;
        }

        if (Objects.nonNull(node.left)) {
            clear(node.left);
            node.left = null;
        }

        if (Objects.nonNull(node.right)) {
            clear(node.right);
            node.right = null;
        }

        node.clear();
    }

    /**
     * clean travel data which load by weak reference
     * @param mapWeakReference  map which build with weak reference
     */
    private void clear(WeakReference<Map<Object, Object>> mapWeakReference) {
        if(Objects.isNull(mapWeakReference)){
            return;
        }

        Map clearMap = mapWeakReference.get();
        if(Objects.nonNull(clearMap)){
            clearMap.clear();
        }

    }

    /**
     * direct execute gc
     */
    private void gc() {
        weakMap.clear();
    }

    private Node rotate(Node node) {
        if (!isRed(node.left) && isRed(node.right)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private Node getNode(Node node, K key) {
        if (Objects.isNull(node)) {
            return null;
        }
        int compare = getCompare(node, key);
        if (compare == 0) {
            return node;
        }
//        else if (compare < 0) {
//            return getNode(node.left, key);
//        } else { // if compare > 0
//            return getNode(node.right, key);
//        }

        Node left = getNode(node.left, key);
        if(Objects.nonNull(left)){
            return left;
        }
        Node right = getNode(node.right, key);
        if(Objects.nonNull(right)){
            return right;
        }
        return null;
    }

    /**
     * @param node any node
     */
    private Node minimum(Node node) {

        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    /**
     * @param node any node
     * @return min node
     */
    private Node removeMin(Node node) {
        if (Objects.isNull(node.left)) {
            return removeRight(node);
        }
        node.left = removeMin(node.left);
        return node;
    }

    private Node removeRight(Node node) {
        Node rightNode = node.right;
        node.right = null;
        size--;
        return rightNode;
    }

    /**
     * @param node any key
     * @param key  key
     * @return
     */
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        int compare = getCompare(node, key);
        if (compare < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (compare > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {//compare == 0
            if (Objects.isNull(node.left)) {
                return removeRight(node);
            }

            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;

            return successor;

        }
    }



    private void print(Node node) {
        if (Objects.isNull(node)) {
            return;
        }
        node.print();
        print(node.left);
        print(node.right);
    }

    private List<Node> getChild(Node node) {
        List<Node> ret = new ArrayList<>();
        if (Objects.nonNull(node.left)) {
            ret.add(node.left);
        }
        if (Objects.nonNull(node.right)) {
            ret.add(node.right);
        }
        return ret;
    }

//    private void print(List<List<Node>> nodes) {
//        StringBuilder sb = new StringBuilder();
//        List<List<Node>> ret = new ArrayList<>();
//
//        for (List<Node> nodePair : nodes) {
//            sb.append("[");
////            sb.append(Joiner.on(",").join(getStringPair(nodePair)));
//            sb.append("]");
//            nodePair.forEach(nodeChild -> {
//                List<Node> children = getChild(nodeChild);
//                if (Objects.nonNull(children) && !children.isEmpty()) {
//                    ret.add(children);
//                }
//            });
//        }
//        Sys.p(sb.toString());
//        if (!nodes.isEmpty()) {
//            print(ret);
//        }
//    }
//
//    private List<String> getStringPair(List<Node> nodePair) {
//        List<String> printStr = new ArrayList<>();
//        nodePair.forEach(node -> {
//                    printStr.add(node.key.toString());
//                    printStr.add(node.value.toString());
//                }
//        );
//        return printStr;
//    }

    private int getCompare(Node node, K key) {
        return key.compareTo(node.key);
    }

}
