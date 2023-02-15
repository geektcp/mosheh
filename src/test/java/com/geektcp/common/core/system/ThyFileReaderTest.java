package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Map;

public class ThyFileReaderTest {

    @Test
    public void test2(){
        Sys.p("111: {}", Sys.getResourcePath());
        Sys.p("222: {}", Sys.getResourceRootPath());
        Sys.p("444: {}", Sys.getResourcePath("dist.json"));
        Sys.p("555: {}", Sys.getResourcePath("/aaa/dist.json"));
        Sys.p("666: {}", Sys.getResourcePath("ThyFileSystem.class"));

        Assert.assertTrue(true);
    }

    @Test
    public void read(){
        Map<String, Object> map =  Sys.readXmlFile(Sys.getResourcePath("test.xml"));



    }
}
