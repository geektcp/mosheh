package com.geektcp.common.core.system;

import com.geektcp.common.core.util.JsonUtils;

import java.util.Properties;

/**
 * @author geektcp on 2023/2/4 23:37.
 */
public class SysTest {
    public static void main(String[] args) {
        Properties prop = System.getProperties();
//        Sys.println(JsonUtils.toString(prop));
        Sys.println(Sys.availableProcessors());

        Sys.println(Sys.maxMemory());
        Sys.println(Sys.totalMemory());

        Sys.print(Sys.getPath());

    }


}
