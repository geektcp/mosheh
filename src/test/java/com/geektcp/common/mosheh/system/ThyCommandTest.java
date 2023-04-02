package com.geektcp.common.mosheh.system;

import org.junit.Assert;
import org.junit.Test;

public class ThyCommandTest {

    @Test
    public void testPrint() throws Exception {

        Process process = Sys.getCommandBuilder()
                .runDir("/tmp")
                .program("netstat")
                .arg("-a")
                .arg("-n")
                .arg("-t")
                .arg("-p")
                .start();
        Sys.printCommandResult(process);

        Assert.assertTrue(true);
    }


    @Test
    public void testExec() throws Exception {
        Process process = Sys.exec("ls -l");
        Sys.printCommandResult(process);

        Assert.assertTrue(true);
    }

    @Test
    public void testExec2() throws Exception {
        Process process = Sys.getCommandBuilder()
                .program("pwd")
                .start();
        Sys.printCommandResult(process);

        Assert.assertTrue(true);
    }

    @Test
    public void testExec3() throws Exception {
        Process process = Sys.getCommandBuilder()
                .runDir("/tmp")
                .program("netstat")
                .arg("-a")
                .arg("-n")
                .arg("-t")
                .arg("-p")
                .start();
        Sys.printCommandResult(process);

        Assert.assertTrue(true);
    }



}
