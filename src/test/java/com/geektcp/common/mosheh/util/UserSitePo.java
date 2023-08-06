package com.geektcp.common.mosheh.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Geektcp  2021/9/23 14:09
 */
public class UserSitePo implements Sortable {

    private String id;

    private String userBaseId;

    private String url;

    private String name;

    private String description;

    private String logo;

    private String siteLabelId;

    private int sort;


    public Integer getSort(){
        return sort;
    }


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setSort(int sort){
        this.sort = sort;
    }
}
