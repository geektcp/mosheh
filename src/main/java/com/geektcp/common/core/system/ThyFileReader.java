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
import java.util.List;
import java.util.Map;

class ThyFileReader {

    private static final String CHARSET = "utf-8";

    public static <T> T readJSONObject(String fileName, Class<T> cls) {
        String str = readTextFile(fileName);
        return JSON.parseObject(str, cls);
    }

    public static List<Map<String, Object>> readListMap(String fileName) {
        return JSON.parseObject(readTextFile(fileName), new TypeReference<List<Map<String, Object>>>() {
        });
    }


    public static String readTextFile(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream readInputStream = new FileInputStream(filePath);
            InputStreamReader read = new InputStreamReader(readInputStream, CHARSET);
            BufferedReader bufferedReader = new BufferedReader(read);

            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                result.append(lineTxt);
                result.append("\n");
            }
            read.close();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
        return result.toString();
    }

    public static <T> T readObject(String fileName, Class<T> objectType) {
        return JSON.parseObject(readTextFile(fileName), objectType);
    }

    public static Map<String, Object> readXmlFile(String filePath){
        Element root = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputStream);
            root = doc.getDocumentElement();
            inputStream.close();
        }catch (Exception e){
            Sys.p(e);
        }
        NodeList nodeList = root.getElementsByTagName("CountryRegion");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String countryname = element.getAttribute("Name");
                if (countryname.equals("中国")) {
                    NodeList n2 = element.getChildNodes();
                    System.out.println("NodeList长度，猜测是节点+孩子的总数："+n2.getLength());
                    //总省份数
                    int count = 0;
                    //遍历省份信息
                    for (int j = 0; j < n2.getLength(); j++) {
                        Node node2 = n2.item(j);
                        if (node2 instanceof Element) {
                            count++;
                            Element e2 = (Element) node2;
                            //获取省份信息
                            String provincename = e2.getAttribute("Name");
                            String code = e2.getAttribute("Code");
                            System.out.println(provincename + "==" + code);
                            //
                            NodeList city = e2.getElementsByTagName("City");
                            for (int k = 0; k < city.getLength(); k++) {
                                Node node3 = city.item(k);
                                if (node3 instanceof Element){
                                    Element c = (Element) node3;
                                    //获取城市信息
                                    String cityname = c.getAttribute("Name");
                                    System.out.println("##"+cityname);
                                    //
                                    NodeList region = c.getElementsByTagName("Region");
                                    for (int m = 0; m < region.getLength(); m++) {
                                        Node node4 = region.item(m);
                                        if (node4 instanceof Element){
                                            Element r = (Element)node4;
                                            String regionname = r.getAttribute("Name");
                                            System.out.println("#####"+regionname);
                                        }

                                    }

                                }
                            }
                        }
                    }
                    System.out.println("总省份数目："+count);

                }
            }


        }

        return null;
    }

}
