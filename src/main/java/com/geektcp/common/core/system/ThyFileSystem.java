package com.geektcp.common.core.system;

/**
 * @author geektcp on 2023/2/4 23:46.
 */
class ThyFileSystem {

    private ThyFileSystem() {
    }

    public static String getResourcePath() {
        return getResourcePath("");
    }

    public static String getResourcePath(String name){
        return ThyFileSystem.class.getResource(name).getPath();
    }

}
