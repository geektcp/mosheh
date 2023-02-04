package com.geektcp.common.core.system;



/**
 * @author geektcp on 2023/2/4 22:32.
 */
public class Sys {

    public static void print(Object str) {
        ThyStream.println(str);
    }

    public static void println(Object str) {
        ThyStream.print(str);
    }

    public static void print(String str) {
        ThyStream.print(str);
    }

    public static void printf(String format, Object... args) {
        ThyStream.printf(format, args);
    }

    public static long maxMemory() {
      return ThyRuntime.maxMemory();
    }

    /**
     * return usable total memory in Java virtual machine
     */
    public static long totalMemory() {
        return ThyRuntime.totalMemory();
    }

    /**
     * return usable free memory in Java virtual machine
     */
    public static long freeMemory() {
        return ThyRuntime.freeMemory();
    }


    public static String getPath() {
        return ThyFileSystem.getPath("");
    }

    public static String getPath(String name){
        return ThyFileSystem.getPath(name);
    }

    public static int availableProcessors() {
        return ThyRuntime.availableProcessors();
    }

}
