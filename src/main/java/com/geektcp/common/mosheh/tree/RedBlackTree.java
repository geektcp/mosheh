package com.geektcp.common.mosheh.tree;


import com.alibaba.fastjson2.JSON;
import com.geektcp.common.mosheh.system.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author geektcp on 2019/9/16.
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    private class Node {
        K key;
        V value;
        Node left;
        Node right;
        boolean color;

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

        public void init(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void print() {
            Sys.p("key: {} | value: {}", key.toString(), value);
        }
    }

    private Node root;

    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    protected void init(){
        this.root = new Node();
    }

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
     * look at x
     * node                                       x
     * /    \              left                  /    \
     * T1     x           --------->            node    T3
     * /   \                              /    \
     * T2     T3                           T1    T2
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
     * look at x
     * node                                       x
     * /    \              right                 /    \
     * x     T2           --------->             y     node
     * /  \                                             /    \
     * y   T1                                           T1    T2
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


    private Node add(Node node, K key, V value) {
        if (Objects.isNull(node) || node.isEmpty()) {
            size++;
            node.init(key, value);
            return node;
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
            return node;
        }
        if (Objects.isNull(node.right)) {
            node.right = new Node();
            add(node.right, key, value);
            return node;
        }

        return add(node.left, key, value);

    }

    private boolean addLeftChild(Node node, K key, V value) {
        add(node.left, key, value);
        return true;
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
        if (node == null) {
            return null;
        }
        int compare = getCompare(node, key);
        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return getNode(node.left, key);
        } else { // if compare > 0
            return getNode(node.right, key);
        }
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

    private void print(List<List<Node>> nodes) {
        StringBuilder sb = new StringBuilder();
        List<List<Node>> ret = new ArrayList<>();

        for (List<Node> nodePair : nodes) {
            sb.append("[");
//            sb.append(Joiner.on(",").join(getStringPair(nodePair)));
            sb.append("]");
            nodePair.forEach(nodeChild -> {
                List<Node> children = getChild(nodeChild);
                if (Objects.nonNull(children) && !children.isEmpty()) {
                    ret.add(children);
                }
            });
        }
        Sys.p(sb.toString());
        if (!nodes.isEmpty()) {
            print(ret);
        }
    }

    private List<String> getStringPair(List<Node> nodePair) {
        List<String> printStr = new ArrayList<>();
        nodePair.forEach(node -> {
                    printStr.add(node.key.toString());
                    printStr.add(node.value.toString());
                }
        );
        return printStr;
    }

    private int getCompare(Node node, K key) {
        return key.compareTo(node.key);
    }

}
