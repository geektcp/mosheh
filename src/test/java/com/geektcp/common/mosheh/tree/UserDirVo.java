package com.geektcp.common.mosheh.tree;

import com.geektcp.common.mosheh.tree.node.AbstractListNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Geektcp 2021/9/24 9:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDirVo<K extends Comparable> extends AbstractListNode<K> {

    private String title;

    private String type;

    private String url;

    private String description;


    public String toString() {
        return super.toString();
    }

    public boolean add(AbstractListNode childNode) {
        return true;
    }

}
