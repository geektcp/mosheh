package com.geektcp.common.mosheh.system;


import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/15 0:27.
 */
public class ThyEncryptTest {


    @Test
    public void test() {
        Sys.p(Sys.encrypt("1111"));
        Sys.p(Sys.decrypt(
                "0ff2aaffd3ecf1b3b24dd04b6046424351b6038c8136286a2738c2ec7d0fc5988b1f6a23da0cd" +
                        "5a44fb2e74b2bf63dd5c40adf4965ca1df0bd38838b0718b3f287aed7cd945235dc1cc7c8bb" +
                        "bb3775aee2a9157a5299a2151e4c691fd7f4d1b3e3dad04aa6429d4db67092711158aac1eb6" +
                        "ec06f2ee0133cd05f7a4b75487939"));
        Assert.assertTrue(true);
    }

}
