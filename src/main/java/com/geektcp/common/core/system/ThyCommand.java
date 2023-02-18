/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geektcp.common.core.system;

import com.geektcp.common.core.collection.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author geektcp on 2022/2/5.
 */
class ThyCommand {

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
        String[] cmdArray = Lists.toArray(cmdList, String.class);
        Process process = null;
        try {
            process = new ProcessBuilder(cmdArray)
                    .directory(dir)
                    .start();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

        return process;
    }

    /**
     *
     * @param cmdArray single command which is String[] type
     * @param dir run command dir
     * @return result Stream
     */
    public static Process execute(String[] cmdArray, File dir) {
        Process process = null;
        try {
            process = new ProcessBuilder(cmdArray)
                    .directory(dir)
                    .start();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }

        return process;
    }

    /**
     * print the result of execute command
     * @param process Stream
     */
    public static void printCommandResult(Process process)  {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                Sys.p(line);
            }
        }catch (Exception e){
            Sys.p(e.getMessage());
        }
    }

}
