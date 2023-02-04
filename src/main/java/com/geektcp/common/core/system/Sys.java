package com.geektcp.common.core.system;


import java.util.Properties;

/**
 * @author geektcp on 2023/2/4 22:32.
 */
public class Sys {

    private Sys() {
    }


    ////////////// PrintStream ////////////////
    public static void print(Object str) {
        ThyStream.println(str);
    }

    public static void println(Object str) {
        ThyStream.print(str);
    }

    public static void println(String str) {
        ThyStream.println(str);
    }

    public static void print(String str) {
        ThyStream.print(str);
    }

    public static void print(long n) {
        ThyStream.print(n);
    }

    public static void printf(String format, Object... args) {
        ThyStream.printf(format, args);
    }


    ////////////// Runtime JDK ////////////////
    public static int availableProcessors() {
        return ThyRuntime.availableProcessors();
    }
    /**
     * return used max memory in Java virtual machine
     */
    public static long maxMemory() {
        return ThyRuntime.maxMemory();
    }

    /**
     * return total memory in Java virtual machine
     */
    public static long totalMemory() {
        return ThyRuntime.totalMemory();
    }

    /**
     * return free memory in Java virtual machine
     */
    public static long freeMemory() {
        return ThyRuntime.freeMemory();
    }


    ////////////// FileSystem ////////////////
    public static String getResourcePath() {
        String resourcePath = ThyFileSystem.getResourcePath("/");
        if(isWindows() && resourcePath.startsWith("/")){
           return resourcePath.substring(1);
        }
        return resourcePath;
    }

    public static String getResourcePath(String name) {
        return ThyFileSystem.getResourcePath(name);
    }


    ////////////// System Properties ////////////////
    public static Properties getProperties() {
        return ThySystemProperties.getProperties();
    }

    public static String getUserHome() {
        return ThySystemProperties.getUserHome();
    }

    public static String getUserDir() {
        return ThySystemProperties.getUserDir();
    }

    public static String getUserName() {
        return ThySystemProperties.getUserName();
    }

    public static String getUserLanguage() {
        return ThySystemProperties.getUserLanguage();
    }

    public static String getUserTimezone() {
        return ThySystemProperties.getUserTimezone();
    }

    public static String getOsName() {
        return ThySystemProperties.getOsName();
    }

    public static String getOsVersion() {
        return ThySystemProperties.getOsName();
    }

    public static String getJdkVersion() {
        return ThySystemProperties.getJdkVersion();
    }

    public static String getFileEncoding() {
        return ThySystemProperties.getFileEncoding();
    }

    public static String getOsArch() {
        return ThySystemProperties.getOsArch();
    }

    public static String getJdkArch() {
        return ThySystemProperties.getJdkArch();
    }

    public static int getOsBit() {
        return ThySystemProperties.getOsBit();
    }

    public static int getJdkBit() {
        return ThySystemProperties.getJdkBit();
    }

    public static String getJavaClassPath() {
        return ThySystemProperties.getJavaClassPath();
    }

    public static String getFileSeparator() {
        return ThySystemProperties.getFileSeparator();
    }

    public static boolean isWindows() {
        return ThySystemProperties.isWindows();
    }

    public static boolean isLinux() {
        return ThySystemProperties.isLinux();
    }


    ////////////// Env Properties ////////////////
    public static String getEnv(String envName) {
        return ThyEnv.getEnv(envName);
    }

}
