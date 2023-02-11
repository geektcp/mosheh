package com.geektcp.common.core.system;

import java.io.File;

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

    public static boolean mv(String src, String dst) {
        File srcFile = new File(src);
        File dstFile = new File(dst);

        if (!srcFile.exists()) {
            return false;
        }

        return srcFile.renameTo(dstFile);
    }

    public static boolean rm(String file) {
        File f = new File(file);
        return f.delete();
    }

    public static boolean mkdirs(String file) {
        return mkdir(file);
    }

    public static boolean mkdir(String file) {
        boolean ret = false;
        File f = new File(file);
        if(f.exists()){
            return false;
        }
        if (f.isDirectory()) {
            return  f.mkdirs();
        }
        File parentFile = f.getParentFile();
        if(!parentFile.exists()){
            ret = parentFile.mkdirs();
        }

        return ret;
    }

    public static boolean touch(String file) {
        boolean ret = false;
        if(!mkdir(file)){
            return false;
        }
        File f = new File(file);
        if(f.exists()){
            return false;
        }
        try {
            ret = f.createNewFile();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

        return ret;
    }

}
