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
