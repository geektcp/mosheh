package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.system.Sys;
import org.junit.Assert;

/**
 * @author geektcp on 2023/2/15 0:55.
 */
public class ThyEncryptTest {


    public static void main(String[] args) {
        test();
    }


    public static void test(){
        Sys.p(Sys.encrypt("11111"));
        Sys.p(Sys.decrypt("41f552c89c3b81c8c787740e7f43ed4a0e902fa07726975d7ddc365585a81e1bd5c4914de772b9467f6abeb52c551bec3ca0454b440ae852145c843a0b427d33f1cef64734a54d9f34975b5b8706e52f724760b0441900c20c60f7e6b1a9e19c571a719d6be3210bfb24489bf8a0efaed7229d11c5bf2f508b2d37fabb56b194"));
        Assert.assertTrue(true);
    }


    public static void test2(){
        Sys.p(Sys.encrypt("11111"));
        Sys.p(Sys.decrypt("10604891205ea5b7aa5fdd72808da3d02fec4846e4b9fd4c9d58e4ecb4b0a4a5bc76ffb60e795efd90e580a620aef77da1cd123a9984b5981277181c984aba28c29c303f5a0b7204b9b9956e35260e41441e42a4b53d44ea2c972786dc018e274e05fbc63ba024cddf94998b5f4469aac033982d0187557da5a5061e70553fa8"));
        Assert.assertTrue(true);
    }
}
