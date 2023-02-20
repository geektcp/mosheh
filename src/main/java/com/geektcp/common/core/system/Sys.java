/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geektcp.common.core.system;


import java.io.File;
import java.security.Key;
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

    /**
     * @param str any string which will be print
     * @author geektcp
     */
    public static void p(Object str) {
        ThyStream.p(str);
    }

    public static void p(String messagePattern, Object... args) {
        ThyStream.p(messagePattern, args);
    }

    public static void p(String str) {
        ThyStream.println(str);
    }

    public static void println(Object str) {
        ThyStream.print(str);
    }

    public static void println(String str) {
        ThyStream.println(str);
    }

    public static void print(Object str) {
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

    /**
     * @return number of CPUs of the machine
     * @author geektcp
     */
    public static int availableProcessors() {
        return ThyRuntime.availableProcessors();
    }

    /**
     * @return used max memory in Java virtual machine
     */
    public static long maxMemory() {
        return ThyRuntime.maxMemory();
    }

    /**
     * @return total memory in Java virtual machine
     */
    public static long totalMemory() {
        return ThyRuntime.totalMemory();
    }

    /**
     * @return free memory in Java virtual machine
     */
    public static long freeMemory() {
        return ThyRuntime.freeMemory();
    }

    public static void gc() {
        ThyRuntime.gc();
    }


    ////////////// ThyResource ////////////////
    private static String filterPath(String path) {
        if (Objects.isNull(path)) {
            return null;
        }
        if (isWindows() && path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }

    /**
     * @return the absolute path of resource dir
     * @author geektcp
     */
    public static String getResourceRootPath() {
        return filterPath(ThyResource.getResourceRootPath());
    }

    /**
     * @return the absolute path of resource dir
     * @since 1.0.3.RELEASE
     */
    public static String getResourcePath() {
        return filterPath(ThyResource.getResourcePath());
    }

    /**
     * @param name resource file name
     * @return the absolute path of resource dir
     */
    public static String getResourcePath(String name) {
        return filterPath(ThyResource.getResourcePath(name));
    }

    public static String getResourceClassPath() {
        return filterPath(ThyResource.getResourceClassPath());
    }

    public static String getResourceClassPath(String name) {
        return filterPath(ThyResource.getResourceClassPath(name));
    }

    public static String getResourceClassPath(Class<?> cls) {
        return filterPath(ThyResource.getResourceClassPath(cls));
    }

    public static String getResourceClassPath(String name, Class<?> cls) {
        return filterPath(ThyResource.getResourceClassPath(name, cls));
    }


    ////////////// FileSystem ////////////////

    /**
     * remove file from src to dst
     *
     * @param src source file path
     * @param dst destination file path
     * @return execute result:true or false
     */
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

    public static String readTextFile(String filePath, String filter) {
        return ThyFileReader.readTextFile(filePath, filter);
    }

    public static String readTextFile(String filePath, String filter, boolean isFeedLine) {
        return ThyFileReader.readTextFile(filePath, filter, isFeedLine);
    }

    public static String readPrivateKeyFile(String filePath) {
        return ThyFileReader.readPrivateKeyFile(filePath);
    }

    public static String readPublicKeyFile(String filePath) {
        return ThyFileReader.readPublicKeyFile(filePath);
    }

    public static <T> T readObject(String fileName, Class<T> cls) {
        return ThyFileReader.readObject(fileName, cls);
    }

    public static Map<String, Object> readXmlFile(String fileName) {
        return ThyFileReader.readXmlFile(fileName);
    }


    ////////////// Class ////////////////

    /**
     * @param className class, example: "com.xxx.system.Sys"
     * @return Class
     */
    public static Class<?> getClass(String className) {
        return ThyClass.getClass(className);
    }

    /**
     * @param className class, example: "com.xxx.system.Sys"
     * @return the path of class
     */
    public static String getClassPath(String className) {
        return filterPath(ThyClass.getClassPath(className));
    }

    /**
     * @param cls Class, example: Sys.class
     * @return the path of class
     */
    public static String getClassPath(Class<?> cls) {
        return ThyClass.getClassPath(cls);
    }


    ////////////// System Properties ////////////////
    public static Properties getProperties() {
        return ThyProperties.getProperties();
    }

    public static String getUserHome() {
        return ThyProperties.getUserHome();
    }

    public static String getUserDir() {
        return ThyProperties.getUserDir();
    }

    public static String getUserName() {
        return ThyProperties.getUserName();
    }

    public static String getTmpdir() {
        return ThyProperties.getTmpdir();
    }

    public static String getUserLanguage() {
        return ThyProperties.getUserLanguage();
    }

    public static String getUserTimezone() {
        return ThyProperties.getUserTimezone();
    }

    public static String getOsName() {
        return ThyProperties.getOsName();
    }

    public static String getOsVersion() {
        return ThyProperties.getOsName();
    }

    public static String getJdkVersion() {
        return ThyProperties.getJdkVersion();
    }

    public static String getFileEncoding() {
        return ThyProperties.getFileEncoding();
    }

    public static String getOsArch() {
        return ThyProperties.getOsArch();
    }

    public static String getJdkArch() {
        return ThyProperties.getJdkArch();
    }

    public static int getOsBit() {
        return ThyProperties.getOsBit();
    }

    public static int getJdkBit() {
        return ThyProperties.getJdkBit();
    }

    public static String getJavaClassPath() {
        return ThyProperties.getJavaClassPath();
    }

    public static String getFileSeparator() {
        return ThyProperties.getFileSeparator();
    }

    public static boolean isWindows() {
        return ThyProperties.isWindows();
    }

    public static boolean isLinux() {
        return ThyProperties.isLinux();
    }


    ////////////// Env Properties ////////////////

    /**
     * only get evv , can not set env
     * because env init when java process start
     */
    public static String getEnv(String envName) {
        return ThyEnv.getEnv(envName);
    }


    ////////////// command execute ////////////////

    /**
     * example:
     * Process process = Sys.getCommandBuilder()
     * .runDir("/tmp")
     * .program("netstat")
     * .arg("-a")
     * .arg("-n")
     * .arg("-t")
     * .arg("-p")
     * .start();
     * Sys.printCommandResult(process);
     *
     * @return ThyCommand instance
     */
    public static ThyCommand getCommandBuilder() {
        return ThyCommand.getInstance();
    }

    /**
     * example:
     * Process process = Sys.exec("ls -l");
     * Sys.printCommandResult(process);
     *
     * @param cmd shell or bat command
     * @return the stream which contain result
     */
    public static Process exec(String cmd) {
        return ThyRuntime.exec(cmd);
    }

    /**
     * print the result of execute command
     *
     * @param process steam which contain the result of command
     * @see #getCommandBuilder()
     */
    public static void printCommandResult(Process process) {
        ThyCommand.printCommandResult(process);
    }


    ////////////// ThyHost ////////////////
    public static String getPublicIp() {
        return ThyHost.getPublicIp();
    }

    public static String getPrivateIp() {
        return ThyHost.getPrivateIp();
    }

    public static String getHostName() {
        return ThyHost.getHostName();
    }

    public static String getLoopbackIp() {
        return ThyHost.getLoopbackIp();
    }

    ////////////// ThyEncrypt ////////////////
    public static void initKey(String privateKeyFilename, String publicKeyFilename) {
        ThyEncrypt.initKey(privateKeyFilename, publicKeyFilename);
    }

    /**
     * @param str destination string which will be encrypt with RSA
     * @return the result string which encrypted
     * @see #initKey(String, String)
     * <p>
     * example:
     * Sys.initKey(id_rsa,id_rsa.pub);
     * Sys.encrypt("some");
     */
    public static String encrypt(String str) {
        return ThyEncrypt.encrypt(str);
    }

    /**
     * @param str destination string which encrypted with RSA
     * @return the result string which encrypted
     * @see #initKey(String, String)
     * <p>
     * example:
     * Sys.initKey(id_rsa,id_rsa.pub);
     * Sys.decrypt("some");
     */
    public static String decrypt(String str) {
        return ThyEncrypt.decrypt(str);
    }

    public static Key buildKey(String secret) {
        return ThyEncrypt.buildKey(secret);
    }

    public static String desEncrypt(String source) {
        return ThyEncrypt.desEncrypt(source);
    }

    public static String desDecrypt(String source) {
        return ThyEncrypt.desDecrypt(source);
    }

    ////////////// ThyString ////////////////
    public static void setStringSeparator(String separator) {
        ThyString.setStringSeparator(separator);
    }

    /**
     * @param src     source String
     * @param keyword the keyword
     * @return if contain keyword,return true,or false
     */
    public static boolean contains(String src, String keyword) {
        return ThyString.contains(src, keyword);
    }

    public static boolean contains(String src, String... keywords) {
        return ThyString.contains(false, false, false, src, keywords);
    }

    public static boolean contains(boolean orderly, String src, String... keywords) {
        return ThyString.contains(orderly, false, false, src, keywords);
    }

    public static boolean contains(boolean orderly, boolean isIgnoreCase, String src, String[] keywords) {
        return ThyString.contains(orderly, false, isIgnoreCase, src, keywords);
    }

    /**
     * @param orderly  is or not orderly match
     * @param isContinuous is or not continuous, for example: the sequence is 3,4,5,6, or 21,22,23
     * @param isIgnoreCase is or not ignore case
     * @param src the destination string
     * @param keywords the keyword
     * @return bool: true or false
     */
    public static boolean contains(boolean orderly, boolean isContinuous, boolean isIgnoreCase, String src, String[] keywords) {
        return ThyString.contains(orderly, false, isIgnoreCase, src, keywords);
    }

    public static List<String> arrayToList(String[] stringArray, boolean isIgnoreCase) {
        return ThyString.arrayToList(stringArray, isIgnoreCase);
    }

    public static boolean containWithOrder(List<String> wordList, List<String> keywordList) {
        return ThyString.containWithOrder(wordList, keywordList);
    }

    public static boolean containWithoutOrder(List<String> wordList, List<String> keywordList) {
        return ThyString.containWithoutOrder(wordList, keywordList);
    }

    public static boolean find(String src, String regex) {
        return ThyString.find(src, regex);
    }

    public static boolean matches(String src, String regex) {
        return ThyString.matches(src, regex);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            p(e.getMessage());
        }
    }

}
