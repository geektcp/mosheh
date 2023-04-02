package com.geektcp.common.mosheh.socket;



import com.geektcp.common.mosheh.socket.server.Server;

import java.io.IOException;

/**
 * Hello Server!!!
 */
public class Application {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello Server!!!");

        Server srv = new Server();
        try {
            srv.start();
        } catch (IOException e) {
            srv.stop();
            throw e;
        }

        try {
            srv.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
