package com.geektcp.common.core.tree.st;

/**
 * @author tanghaiyang on 2019/9/28.
 */
public class RedBlackBalancedSearchTree<Key extends Comparable<Key>, Value> extends BalancedSearchTree<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private boolean isRed(Node x) {
        return x!=null && x.color == RED;
    }

    public Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        recalculateSize(h);
        return x;
    }

    public Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        recalculateSize(h);
        return x;
    }

    void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            Node node = new Node(key, value, 1);
            node.color = RED;
            return node;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            x.val = value;
        else if (cmp < 0)
            x.left = put(x.left, key, value);
        else
            x.right = put(x.right, key, value);

        if (isRed(x.right) && !isRed(x.left))
            x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left))
            x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right))
            flipColors(x);

        recalculateSize(x);
        return x;
    }
}
