package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ThyStringTest {

    @Test
    public void test() {
        Pattern pattern = Pattern.compile("Java");
        Matcher matcher1 = pattern.matcher("Python, Java, Go, C++");
        Matcher matcher2 = pattern.matcher("Python, C, Go, Matlab");
        Sys.p(matcher1.find());
        Sys.p(matcher2.find());
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        Pattern keywordPattern = Pattern.compile("123");
        Matcher sourceMatcher = keywordPattern.matcher("1234");
        Sys.p(sourceMatcher.find());
        Assert.assertTrue(true);
    }

    @Test
    public void test3() {
        String keyword = "123";
        String src = "1234";
        Sys.p(src.contains(keyword));
        Assert.assertTrue(true);
    }

    @Test
    public void test4() {
        Sys.p(Pattern.matches(".*som.*", "this is something! something"));
        Assert.assertTrue(true);
    }

    public static void main(String[] args) {
        Sys.p(ThyString.contains(true,"this is something! something", "so", "is", "eth"));
        Sys.p(ThyString.contains(false,"this is something! something", "so", "is", "eth"));

        Sys.p(ThyString.contains(true,"this is something! something", "is", "th", "eth"));
        Sys.p(ThyString.contains(false,"this is something! something", "is", "th", "eth"));
        Assert.assertTrue(true);
    }
}
