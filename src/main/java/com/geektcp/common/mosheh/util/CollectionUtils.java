package com.geektcp.common.mosheh.util;

import java.util.List;
import java.util.Objects;

/**
 * @author geektcp on 2023/8/3 10:02.
 */
public class CollectionUtils {

    public static boolean isEmpty(List list){
        return Objects.isNull(list) || list.isEmpty();
    }

    public static boolean isNotEmpty(List list){
        return !isEmpty(list);
    }

}
