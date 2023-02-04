package com.geektcp.common.core.system;

/**
 * @author geektcp on 2023/2/4 23:47.
 */
public class ThyRuntime {


    /**
     * return usable max memory in Java virtual machine
     */
    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * return usable total memory in Java virtual machine
     */
    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * return usable free memory in Java virtual machine
     */
    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }


    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

}
