package com.geektcp.common.core.system;

import java.io.IOException;

/**
 * @author geektcp on 2023/2/4 23:47.
 */
public class ThyRuntime {

    private ThyRuntime() {
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static Process exec(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            Sys.p(e.getMessage());
        }
        return process;
    }

    public static void gc() {
        Runtime.getRuntime().gc();
    }


}
