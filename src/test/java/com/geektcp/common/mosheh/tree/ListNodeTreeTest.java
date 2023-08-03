package com.geektcp.common.mosheh.tree;

import com.geektcp.common.mosheh.collection.Lists;
import com.geektcp.common.mosheh.system.Sys;

import java.util.List;

/**
 * @author geektcp on 2023/8/2 22:49.
 */
public class ListNodeTreeTest {


    public static void main(String[] args) throws Exception {
        List<UserDirVo> voList = Lists.newArrayList();

        UserDirVo<String> vo1 = new UserDirVo<>();
        vo1.setTitle("xxx");
        vo1.setId("111");
        vo1.setParentId(null);
        voList.add(vo1);

        UserDirVo<String> vo2 = new UserDirVo<>();
        vo2.setTitle("xxx");
        vo2.setId("222");
        vo2.setParentId(null);
        voList.add(vo2);

        UserDirVo<String> vo3 = new UserDirVo<>();
        vo3.setTitle("xxx");
        vo3.setId("333");
        vo3.setParentId("111");
        voList.add(vo3);

        UserDirVo<String> vo4 = new UserDirVo<>();
//        vo4.setTitle("xxx");
        vo4.setId("444");
        vo4.setParentId("222");
        voList.add(vo4);

        UserDirVo<String> vo5 = new UserDirVo<>();
        vo5.setTitle("xxx");
        vo5.setId("555");
        vo5.setParentId("333");
        voList.add(vo5);

        UserDirVo<String> vo6 = new UserDirVo<>();
        vo6.setTitle("xxx");
        vo6.setId("666");
        vo6.setParentId("444");
        voList.add(vo6);

        UserDirVo<String> vo7 = new UserDirVo<>();
        vo7.setTitle("xxx");
        vo7.setId("777");
        vo7.setParentId("555");
        voList.add(vo7);


        UserDirVo tree = ListNodeTree.createTree(voList, UserDirVo.class);

        Sys.p("{}",tree.toString());
    }



}
