package com.geektcp.common.core.system;


import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public static void p(Object str) {
        ThyStream.p(str);
    }

    public static void p(String messagePattern, Object... args) {
        ThyStream.p(messagePattern, args);
    }

    public static void println(Object str) {
        ThyStream.print(str);
    }

    public static void p(String str) {
        ThyStream.println(str);
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

    public static Process exec(String cmd) {
        return ThyRuntime.exec(cmd);
    }

    public static void gc() {
        ThyRuntime.gc();
    }


    ////////////// FileSystem ////////////////
    private static String filterPath(String path) {
        if (Objects.isNull(path)) {
            return null;
        }
        if (isWindows() && path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }

    public static String getResourceRootPath() {
        return filterPath(ThyFileSystem.getResourceRootPath());
    }

    public static String getResourcePath(String name) {
        return filterPath(ThyFileSystem.getResourcePath(name));
    }

    public static boolean mv(String src, String dst) {
        return ThyFileSystem.mv(src, dst);
    }

    public static boolean rm(String file) {
        return ThyFileSystem.rm(file);
    }

    public static boolean mkdir(String file) {
        return ThyFileSystem.mkdir(file);
    }

    public static boolean mkdirs(String file) {
        return ThyFileSystem.mkdirs(file);
    }

    public static boolean touch(String file) {
        return ThyFileSystem.touch(file);
    }

    public static boolean exists(String file) {
        return ThyFileSystem.exists(file);
    }

    public static List<File> getFiles(String filePath) {
        return ThyFileSystem.getFiles(filePath);
    }

    public static double getDirSize(File file) {
        return ThyFileSystem.getDirSize(file);
    }

    public static void listAllFiles(File dir) {
        ThyFileSystem.listAllFiles(dir);
    }

    public static boolean copyFile(String src, String dst) {
        return ThyFileSystem.copyFile(src, dst);
    }

    public static boolean fastCopy(String src, String dst) {
        return ThyFileSystem.fastCopy(src, dst);
    }

    ////////////// FileReader ////////////////
    public static <T> T readJSONObject(String fileName, Class<T> cls) {
        return ThyFileReader.readJSONObject(fileName, cls);
    }

    public static List<Map<String, Object>> readListMap(String fileName) {
        return ThyFileReader.readListMap(fileName);
    }

    public static String readTextFile(String filePath) {
        return ThyFileReader.readTextFile(filePath);
    }

    public static <T> T readObject(String fileName, Class<T> cls) {
        return ThyFileReader.readObject(fileName, cls);
    }

    public static Map<String, Object> readXmlFile(String fileName){
        return ThyFileReader.readXmlFile(fileName);
    }


    ////////////// Class ////////////////
    public static Class<?> getClass(String className) {
        return ThyClass.getClass(className);
    }

    public static String getClassPath(String className) {
        return filterPath(ThyClass.getClassPath(className));
    }

    public static String getClassPath(Class<?> cls) {
        return ThyClass.getClassPath(cls);
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

    public static String getTmpdir() {
        return ThySystemProperties.getTmpdir();
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


    ////////////// command execute ////////////////
    public static ThyCommand getCommandBuilder() {
        return ThyCommand.getInstance();
    }

    public static void printCommandResult(Process process) {
        ThyCommand.printCommandResult(process);
    }

}
