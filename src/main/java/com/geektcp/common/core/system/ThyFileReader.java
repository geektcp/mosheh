package com.geektcp.common.core.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class ThyFileReader {

    private ThyFileReader() {
    }


    public static <T> T readJSONObject(String fileName, Class<T> cls) {
        String str = readTextFile(fileName);
        return JSON.parseObject(str, cls);
    }

    public static List<Map<String, Object>> readListMap(String fileName) {
        return JSON.parseObject(readTextFile(fileName), new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static String readPrivateKeyFile(String filePath) {
        return readTextFile(filePath, "PRIVATE KEY", false);
    }

    public static String readPublicKeyFile(String filePath) {
        return readTextFile(filePath, "PUBLIC KEY", false);
    }

    public static String readTextFile(String filePath) {
        return readTextFile(filePath, null);
    }

    public static String readTextFile(String filePath, String filter) {
        return readTextFile(filePath, filter, true);
    }

    public static String readTextFile(String filePath, String filter, boolean isFeedLine) {
        StringBuilder result = new StringBuilder();
        boolean isFilter = true;
        if (Objects.isNull(filter)) {
            isFilter = false;
        }
        try {
            InputStream readInputStream = new FileInputStream(filePath);
            InputStreamReader read = new InputStreamReader(readInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(read);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isFilter && line.contains(filter)) {
                    continue;
                }
                result.append(line);
                if (isFeedLine) {
                    result.append("\n");
                }
            }
            read.close();
            readInputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
        return result.toString();
    }

    public static <T> T readObject(String fileName, Class<T> objectType) {
        return JSON.parseObject(readTextFile(fileName), objectType);
    }

    public static Map<String, Object> readXmlFile(String filePath) {
        Element root = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            inputStream.close();
        } catch (Exception e) {
            Sys.p(e);
        }
        parse(root);

        return null;
    }

    private static void parse(Element root) {
        NodeList dependenciesList = root.getElementsByTagName("dependencies");
        Sys.p(dependenciesList.getLength());
        Node dependencies = dependenciesList.item(0);
        Node dependenceNode = dependencies.getFirstChild();
        Node dependence = dependenceNode.getNextSibling();

        Node groupIdNode = dependence.getFirstChild();
        Node groupId = groupIdNode.getNextSibling();

        Node eleNode = groupId.getFirstChild();
        String ele = eleNode.getNodeValue();

        Sys.p(ele);


        String ret = root.getElementsByTagName("dependencies").item(0)
                .getFirstChild().getNextSibling()
                .getFirstChild().getNextSibling()
                .getFirstChild().getNodeValue();
        Sys.p(ret);

    }

}
