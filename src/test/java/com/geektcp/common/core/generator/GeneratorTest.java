package com.geektcp.common.core.generator;

import com.geektcp.common.core.system.Sys;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/5 1:55.
 */
public class GeneratorTest {

    @Test
    public void base() {
        long a = 0;
        String c = null;

        a = IdGenerator.getId();
        System.out.println(a);
        a = IdGenerator.getId(1, 3);

        System.out.println(a);
        IdGenerator.setDelimiter("/");
        c = IdGenerator.getId("PRE");

        System.out.println(c);
        IdGenerator.setInstance(10, 0);

        a = IdGenerator.getId();

        System.out.println(a);
        Assert.assertTrue(true);
    }

    @Test
    public void singleGen() {
        for (int i = 0; i < 100; i++) {
            System.out.println(IdGenerator.getId());
        }
        Assert.assertTrue(true);
    }

    @Test
    public void multiThread() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(IdGenerator.getId("A"));
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(IdGenerator.getId("B"));
            }
        }, "A").start();

        Assert.assertTrue(true);
    }
}
