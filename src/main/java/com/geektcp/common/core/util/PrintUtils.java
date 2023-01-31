package com.geektcp.common.core.util;

/**
 * @author Mr.Tang on 2023/2/1 1:26.
 */
public class PrintUtils {

    public static void print(String str) {
        System.out.println(str);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}
