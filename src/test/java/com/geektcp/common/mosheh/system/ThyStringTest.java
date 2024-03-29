package com.geektcp.common.mosheh.system;

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
        Sys.p(ThyString.contains(true, false,true, src, keywords));
        Sys.p(ThyString.contains(true, false,false, src, keywords));
        Sys.p(ThyString.contains(false, false,true, src, keywords));
        Sys.p(ThyString.contains(false, false,false, src, keywords));
        Sys.p("------");
        String[] keywords2 = new String[]{"far", "ne", "GPt"};
        Sys.p(ThyString.contains(true, false,true, src, keywords2));
        Sys.p(ThyString.contains(true, false,false, src, keywords2));
        Sys.p(ThyString.contains(false, false,true, src, keywords2));
        Sys.p(ThyString.contains(false, false,false, src, keywords2));
        Assert.assertTrue(true);
    }


    @Test
    public void test5() {
        String src = "ChatGPT |is| a| new type |of artificial intelligence |robot, which has |far-reaching influence.";
        String[] keywords = new String[]{"pe", "GPt", "far"};
        Sys.setStringSeparator("\\|");
        Sys.p(Sys.contains(true, true, src, keywords));
        Sys.p(Sys.contains(true, false, src, keywords));
        Sys.p(Sys.contains(false, true, src, keywords));
        Sys.p(Sys.contains(false, false, src, keywords));
        Assert.assertTrue(true);
    }

    @Test
    public void test6() {
        String src = "create table student(id int, name string); drop database mydatabase; " +
                "alter table tb drop partition(dt='2022-05-30');drop table if exists employee;";
        String[] keywords = new String[]{"drop", "database"};
        Sys.p(Sys.contains(true, true, src, keywords));
        Sys.p(Sys.contains(true, false, src, keywords));
        Sys.p(Sys.contains(false, true, src, keywords));
        Sys.p(Sys.contains(false, false, src, keywords));

        Sys.p(Sys.matches( src, ".*drop\\s*database.*"));
    }

    @Test
    public void test7() {
        String src = "create table student(id int, name string);drop table if exists employee; show database xxx; " +
                "alter table tb drop partition(dt='2022-05-30');";
        String[] keywords = new String[]{"drop", "database"};
        Sys.p(Sys.contains(true, true, src, keywords));
        Sys.p(Sys.contains(true, false, src, keywords));
        Sys.p(Sys.contains(false, true, src, keywords));
        Sys.p(Sys.contains(false, false, src, keywords));
        Assert.assertTrue(true);
    }

    @Test
    public void test8() {
        String src = "create table student(id int, name string);drop table if exists employee; show database xxx; " +
                "alter table tb drop partition(dt='2022-05-30');";
        Sys.p(Sys.matches( src.toLowerCase(), ".*drop\\s*database.*"));
        Assert.assertTrue(true);
    }

    @Test
    public void test9() {
        String src = "select * \nfrom  tse;\n  \ndrop\tdatabase test_db";
        Sys.p(src.replace("\n",""));
        Sys.p(src.toLowerCase());
        String regex = "[\\s\\S]*drop\\s*database[\\s\\S]*";
        Sys.p(Pattern.matches(regex, src.replace("\n","")));

        Assert.assertTrue(true);
    }

    @Test
    public void test10() {
        String src = "\t\t\t\t";
        String regex = "[\\s]*";
        Sys.p(Pattern.matches(regex, src));

        Assert.assertTrue(true);
    }

}
