package com.geektcp.common.core.system;

import java.util.Map;

/**
 * @author geektcp on 2023/2/5 1:28.
 */
public class ThyEnv {

    private ThyEnv() {
    }

    private static Map<String, String> env = System.getenv();

    /**
     * only get evv , can not set env
     * because env init when java process start
     * */
    public static String getEnv(String name) {
        return env.getOrDefault(name, "");
    }

}
