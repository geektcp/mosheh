package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class ShellTest {

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

    public void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }


}
