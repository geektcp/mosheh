package com.geektcp.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanghaiyang on 2018/1/26.
 */
@Slf4j
public class FileUtils {

    private static final String CHARSET = "utf-8";

    private FileUtils() {
    }

    public static <T> T readJSONObject(String fileName, Class<T> cls) {
        String str = readTxt(fileName);
        return JSON.parseObject(str, cls);
    }

    public static List<Map<String, Object>> readListMap(String fileName) {
        return JSON.parseObject(readTxt(fileName), new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static String readTxt(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream readInputStream = FileUtils.class.getClassLoader().getResourceAsStream(filePath);
            if(Objects.isNull(readInputStream)){
                log.error("resource file is not exit: {}", filePath);
            }
            InputStreamReader read = new InputStreamReader(readInputStream, CHARSET);
            BufferedReader bufferedReader = new BufferedReader(read);

            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                result.append(lineTxt);
                result.append("\n");
            }
            read.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result.toString();
    }



    public static <T> T readObject(String fileName, Class<T> objectType) {
        return JSON.parseObject(readTxt(fileName), objectType);
    }

    /**
     * Recursive access folder all the files below.
     *
     * @param filePath file path
     */
    public static List<File> getFiles(String filePath) {
        List<File> listFile = new ArrayList<>();
        try {
            File file = new File(filePath);
            log.info("Start scanning folder: {} {}", file.getPath(), file.isDirectory());
            if (file.isDirectory()) {
                if (!filePath.endsWith(File.separator)) {
                    filePath += File.separator;
                }
                String[] files = file.list();
                if(Objects.isNull(files)){
                    return Lists.newArrayList();
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
            log.info("Read files has error due to: " + e.getMessage());
        }
        log.info("File list size: {}", listFile.size());
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
            log.warn("File does not exists,{}", file.getPath());
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

    /**
     * @return get resource absolute path
     */
    public static String getResourcePath() {
        return FileUtils.class.getResource("/").getPath();
    }

    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            log.debug("dirname: {}",dir.getName());
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

    public static void copyFile(String src, String dist) throws IOException {
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dist);){
            byte[] buffer = new byte[20 * 1024];
            int cnt;
            while ((cnt = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, cnt);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public static void fastCopy(String src, String dist) throws IOException {
        try(FileInputStream fin = new FileInputStream(src);
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
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


    ////////////////////////////
    private static boolean checkFile(File file) {
        if (!file.isFile()) {
            log.error("file is not a normal file: {}", file.getAbsoluteFile());
            return false;
        }
        if (!file.exists()) {
            log.error("file is not exist: {}", file.getAbsoluteFile());
            return false;
        }

        return true;
    }
}
