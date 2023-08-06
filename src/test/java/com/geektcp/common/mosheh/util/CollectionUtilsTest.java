package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.collection.Lists;
import com.geektcp.common.mosheh.system.Sys;

import java.util.List;


/**
 * @author geektcp on 2023/8/6 14:40.
 */
public class CollectionUtilsTest {

    public static void main(String[] args) {
        List<UserSitePo> list = Lists.newArrayList();

        UserSitePo po1 = new UserSitePo();
        po1.setName("abc");
        po1.setSort(3);
        list.add(po1);

        UserSitePo po2 = new UserSitePo();
        po2.setName("ttttt");
        po2.setSort(8);
        list.add(po2);

        UserSitePo po3 = new UserSitePo();
        po3.setName("xxxx");
        po3.setSort(2);
        list.add(po3);

        UserSitePo po4 = new UserSitePo();
        po4.setName("addddbc");
        po4.setSort(11);
        list.add(po4);

        Class<?> cls = list.getClass();
        List<UserSitePo> ret = CollectionUtils.sort(list);
        Sys.pretty(ret);
        List<UserSitePo> ret1 = CollectionUtils.sort(list, CollectionUtils.SORT_ASC);
        Sys.pretty(ret1);
        List<UserSitePo> ret2 = CollectionUtils.sort(list, CollectionUtils.SORT_DESC);
        Sys.pretty(ret2);

    }


}
