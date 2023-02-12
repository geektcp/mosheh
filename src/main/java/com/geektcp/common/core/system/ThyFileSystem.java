package com.geektcp.common.core.system;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author geektcp on 2023/2/4 23:46.
 */
class ThyFileSystem {

    private ThyFileSystem() {
    }

    public static String getResourceRootPath() {
        return getResourceClassPath("/");
    }

    public static String getResourceClassPath() {
        return getResourceClassPath("");
    }

    public static String getResourceClassPath(String name) {
        URL url =  ThyFileSystem.class.getResource(name);
        if(Objects.isNull(url)){
            return "";
        }
        return url.getPath();
    }

    public static String getResourcePath(String name) {
        String rootPath = getResourceRootPath();
        if(name.startsWith("/")){
            name = name.substring(1);
        }
        return rootPath + name;
    }

    public static boolean mv(String src, String dst) {
        File srcFile = new File(src);
        File dstFile = new File(dst);

        if (!srcFile.exists()) {
            return false;
        }

        return srcFile.renameTo(dstFile);
    }

    public static boolean rm(String file) {
        File f = new File(file);
        return f.delete();
    }

    public static boolean mkdirs(String file) {
        return mkdir(file);
    }

    public static boolean mkdir(String file) {
        File f = new File(file);
        return f.mkdirs();
    }

    public static boolean exists(String file) {
        File f = new File(file);
        return f.exists();
    }

    public static boolean touch(String file) {
        boolean ret = false;
        File f = new File(file);
        if (f.exists()) {
            return false;
        }
        String parent = f.getParent();
        if (!exists(parent)) {
            mkdir(parent);
        }
        try {
            ret = f.createNewFile();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

        return ret;
    }

    public static List<File> getFiles(String filePath) {
        List<File> listFile = new ArrayList<>();
        try {
            File file = new File(filePath);
            Sys.p("Checking folder: " + file.getPath() + " " + file.isDirectory());
            if (file.isDirectory()) {
                if (!filePath.endsWith(File.separator)) {
                    filePath += File.separator;
                }
                String[] files = file.list();
                if (Objects.isNull(files)) {
                    return new ArrayList<>();
                }
                for (String fileStr : files) {
                    String strPath = filePath + fileStr;
                    File tmp = new File(strPath);
                    if (tmp.isDirectory()) {
                        getFileList(listFile, tmp);
                    } else {
                        listFile.add(tmp);
                    }
                }
            } else {
                listFile.add(file);
            }
        } catch (Exception e) {
            Sys.p("Read files has error: " + e.getMessage());
        }
        Sys.p("File list size: " + listFile.size());
        return listFile;
    }

    private static void getFileList(List<File> list, File dir) {
        File[] files = dir.listFiles();
        if (Objects.isNull(files)) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFileList(list, file);
            } else {
                list.add(file);
            }
        }

    }

    public static double getDirSize(File file) {
        double size = 0.0;
        if (!file.exists()) {
            Sys.p("File does not exists: " + file.getPath());
            return size;
        }
        if (!file.isDirectory()) {
            return (double) file.length() / 1024 / 1024;
        }
        File[] children = file.listFiles();
        if (Objects.isNull(children)) {
            return size;
        }
        for (File f : children) {
            size += getDirSize(f);
        }
        return size;
    }


    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            Sys.p("dirname: " + dir.getName());
            return;
        }
        File[] files = dir.listFiles();
        if (Objects.isNull(files)) {
            return;
        }
        for (File file : files) {
            listAllFiles(file);
        }
    }

    public static boolean copyFile(String src, String dist) {
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dist);) {
            byte[] buffer = new byte[20 * 1024];
            int cnt;
            while ((cnt = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, cnt);
            }
        } catch (Exception e) {
            Sys.p(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean fastCopy(String src, String dist){
        try (FileInputStream fin = new FileInputStream(src);
             FileOutputStream fout = new FileOutputStream(dist);
        ) {
            FileChannel fileChannelIn = fin.getChannel();
            FileChannel fileChannelOut = fout.getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int r = fileChannelIn.read(buffer);
                if (r == -1) {
                    break;
                }
                buffer.flip();
                fileChannelOut.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            Sys.p(e.getMessage());
            return false;
        }
        return true;
    }


    ////////////////////////////
    private static boolean checkFile(File file) {
        if (!file.isFile()) {
            Sys.p("file is not a normal file: " + file.getAbsoluteFile());
            return false;
        }
        if (!file.exists()) {
            Sys.p("file is not exist: " + file.getAbsoluteFile());
            return false;
        }

        return true;
    }
}
