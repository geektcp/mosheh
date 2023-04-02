package com.geektcp.common.mosheh.socket;

import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by geektcp on 2018/11/24.
 */

public class ServerTest {

    public static void main(String[] args) throws Exception {
        int port = 999;
        ServerSocket serverSocket = new ServerSocket(port);
        Sys.p("start accept ...");
        Socket connection = serverSocket.accept();
        Sys.p("accepting ...");
        InputStream ins = connection.getInputStream();
        Reader reader = new InputStreamReader(ins, "utf-8");
        BufferedReader br = new BufferedReader(reader);

//        StringBuffer sb = new StringBuffer();
//        for(int c = reader.read(); c!=-1; c=reader.read() ){
//            sb.append((char)c);
//
//            Sys.p("sb: {}", sb);
//        }

//        SelectionKey;
//        DefaultSelectorProvider

        String line = null;
        while (( line = br.readLine() ) != null){
            Sys.p("line: {}", line);

            if(line.equals("") |line.length()==0){
                break;
            }
        }

        OutputStream out =  connection.getOutputStream();
        Writer writer = new OutputStreamWriter(out, "UTF-8");

        String str = "sdfsdfsdfs";

        Sys.p(str);
        writer.write("3333333\r\n");
        writer.flush();

//        out.write((str ).getBytes() );
//        out.flush();

        Thread.sleep(3000);
        connection.close();
        serverSocket.close();

    }




}
