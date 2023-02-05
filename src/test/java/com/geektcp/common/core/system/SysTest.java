package com.geektcp.common.core.system;

import com.geektcp.common.core.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/4 23:37.
 */
public class SysTest {

    @Test
    public void testPrint() {
        Sys.println("aaa");
        Sys.println("123");

        Assert.assertTrue(true);
    }

    @Test
    public void testRuntime() {
        Sys.p(Sys.availableProcessors());
        Sys.p(Sys.maxMemory());
        Sys.p(Sys.totalMemory());

        Assert.assertTrue(true);
    }

    @Test
    public void testProperties() {
        Properties prop = Sys.getProperties();
        Sys.p(JsonUtils.toString(prop));

        Assert.assertTrue(true);
    }

    @Test
    public void testVar() {
        Sys.p(Sys.getResourceRootPath());
        Sys.p(Sys.getUserHome());
        Sys.p(Sys.getFileSeparator());
        Sys.p(Sys.getJdkVersion());
        Sys.p(Sys.getUserHome());
        Sys.p(Sys.getOsName());
        Sys.p(Sys.getUserLanguage());
        Sys.p(Sys.getUserName());

        Assert.assertTrue(true);
    }

    @Test
    public void testEnv() {
        // windows
        Sys.p(Sys.getEnv("JAVA_HOME"));
        Sys.p(Sys.getEnv("Path"));

        // linux
        Sys.p(Sys.getEnv("PATH"));
        Sys.p(Sys.getEnv("HOME"));
        Assert.assertTrue(true);
    }

    @Test
    public void testClass() {
        // windows
        Sys.p(Sys.getClassPath("com.geektcp.common.core.generator.IdGenerator"));

        Assert.assertTrue(true);
    }


    @Test
    public void testList(){
        List<String> list = new ArrayList<>();
        list.add("s1");
        list.add("s2");
        list.add("111");
        list.add("222");
        list.add("333");
        list.add(0,"thy");
        Sys.p(list.get(0));
        Assert.assertTrue(true);
    }

}
