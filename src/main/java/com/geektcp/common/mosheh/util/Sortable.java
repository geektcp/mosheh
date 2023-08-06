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
     * if someone use lombok, check that sort field type must be Integer type.
     * correct example:
     * @Data
     * public class AnyThingPo implements Sortable {
     *     private Integer sort;
     * }
     *
     * wrong example:
     * @Data
     * public class AnyThingPo implements Sortable {
     *     private int sort;  // do not use int type because return type is Integer
     *
     * @return return Integer type ,not int type.
     *
     */
     Comparable getSort();

}
