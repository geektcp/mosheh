package com.geektcp.common.core.system;

import com.geektcp.common.core.collection.Lists;

import java.io.File;
import java.util.ArrayList;

/**
 * @author geektcp on 2022/2/5.
 */
public class ThyCommand {

    private File dir = null;
    private ArrayList<String> cmdList = new ArrayList<>();
    private static ThyCommand instance = new ThyCommand();

    private ThyCommand() {
    }

    public static ThyCommand getInstance(){
        return instance;
    }

    public ThyCommand runDir(String dir) {
        this.dir = new File(dir);
        return this;
    }

    public ThyCommand program(String command) {
        cmdList.add(0, command);
        return this;
    }

    public ThyCommand arg(String arg) {
        cmdList.add(arg);
        return this;
    }

    public Process start() {
        String[] cmdArray = Lists.toArray(String.class, cmdList);
        Process process = null;
        try {
            process = new ProcessBuilder(cmdArray)
                    .directory(dir)
                    .start();
        } catch (Exception e) {
            Sys.p(e.getMessage());
            e.printStackTrace();
        }

        return process;
    }

    public static Process execute(String[] cmdArray, File dir) {
        Process process = null;
        try {
            process = new ProcessBuilder(cmdArray)
                    .directory(dir)
                    .start();
        } catch (Exception e) {
            Sys.p(e.getMessage());
            e.printStackTrace();
        }

        return process;
    }



}
