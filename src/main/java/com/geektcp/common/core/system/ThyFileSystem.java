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

    public static boolean mkdir(String file) {
        boolean ret = false;
        File f = new File(file);
        if (f.isDirectory()) {
            return  f.mkdirs();
        }
        File parentFile = f.getParentFile();
        boolean isMkdir = false;
        if(!parentFile.exists()){
            isMkdir = parentFile.mkdirs();
        }
        if(!isMkdir){
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
