package com.geektcp.common.mosheh.tree;

import com.geektcp.common.mosheh.tree.node.AbstractListNode;
import com.geektcp.common.mosheh.util.CollectionUtils;

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
        List<T> listCopy = (List<T>) deepCopy(list);
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
    /*
     * para list will be modified when excute recursive inserting, so need deepcopy
     *
     * */
    private static Object deepCopy(Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return objInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
