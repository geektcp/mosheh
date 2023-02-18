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
        Sys.p(Sys.contains(keyword));
        Assert.assertTrue(true);
    }

    @Test
    public void test4() {
        Sys.p(Pattern.matches(".*som.*", "this is something! something"));
        Sys.p(Sys.matches("ChatGPT", ".*GPT.*"));
        Assert.assertTrue(true);
    }

    public static void main(String[] args) {
        String src = "ChatGPT is a new type of artificial intelligence robot, which has far-reaching influence.";
        String[] keywords = new String[]{"GPt", "ne", "far"};
        Sys.p(ThyString.contains(true, true, src, keywords));
        Sys.p(ThyString.contains(true, false, src, keywords));
        Sys.p(ThyString.contains(false, true, src, keywords));
        Sys.p(ThyString.contains(false, false, src, keywords));
        Sys.p("------");
        String[] keywords2 = new String[]{"far", "ne", "GPt"};
        Sys.p(ThyString.contains(true, true, src, keywords2));
        Sys.p(ThyString.contains(true, false, src, keywords2));
        Sys.p(ThyString.contains(false, true, src, keywords2));
        Sys.p(ThyString.contains(false, false, src, keywords2));
        Assert.assertTrue(true);
    }


    @Test
    public void test5() {
        String src = "ChatGPT |is| a| new type |of artificial intelligence |robot, which has |far-reaching influence.";
        String[] keywords = new String[]{"pe", "GPt",  "far"};
        Sys.setStringSeparator("\\|");
        Sys.p(Sys.contains(true, true, src, keywords));
        Sys.p(Sys.contains(true, false, src, keywords));
        Sys.p(Sys.contains(false, true, src, keywords));
        Sys.p(Sys.contains(false, false, src, keywords));
        Assert.assertTrue(true);
    }
}
