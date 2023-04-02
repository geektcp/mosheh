package com.geektcp.common.mosheh.socket;

import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author geektcp on 2023/4/2 21:56.
 */
public class SocketServerBuilderTest {


    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            Sys.p("启动服务器....");
            Socket s = ss.accept();
            Sys.p("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            Sys.p("客户端：" + mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write(mess + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
