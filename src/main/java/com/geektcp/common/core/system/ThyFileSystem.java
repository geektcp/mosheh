package com.geektcp.common.core.system;

/**
 * @author geektcp on 2023/2/4 23:46.
 */
public class ThyFileSystem {

    public static String getPath() {
        return getPath("");
    }

    public static String getPath(String name){
        return ThyFileSystem.class.getResource(name).getPath();
    }

}
