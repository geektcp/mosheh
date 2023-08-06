package com.geektcp.common.mosheh.util;

import java.io.Serializable;

/**
 * @author geektcp on 2023/8/6 14:00.
 */
public interface Sortable extends Serializable {

    /**
     * only a method. the sort field can be any name.
     * for example: sorting , id, num etc.
     * just return the sort field as result and it is ok.
     *
     * @return return Integer type ,not int type.
     */
    Integer getSort();

}
