package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
