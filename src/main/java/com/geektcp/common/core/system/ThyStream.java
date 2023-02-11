package com.geektcp.common.core.system;

import java.io.PrintStream;

/**
 * @author geektcp on 2023/2/4 23:43.
 */
class ThyStream {

    private ThyStream() {
    }

    private static final PrintStream out = System.out;
    private static final PrintStream err = System.err;

    public static void setOut(PrintStream out) {
        System.setOut(out);
    }


    public static void print(Object str) {
        out.println(str);
    }

    public static void println(Object str) {
        out.println(str);
    }

    public static void println(String str) {
        out.println(str);
    }

    public static void print(String str) {
        out.print(str);
    }

    public static void print(long n) {
        out.print(n);
    }


    public static void printf(String format, Object... args) {
        out.format(format, args);
    }


}
