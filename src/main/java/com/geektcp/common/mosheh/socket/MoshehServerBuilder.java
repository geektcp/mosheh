package com.geektcp.common.mosheh.socket;

import com.geektcp.common.mosheh.socket.server.MoshehServer;
import com.geektcp.common.mosheh.system.Sys;

import java.io.IOException;

/**
 * @author geektcp on 2023/2/2 1:04.
 */
public class MoshehServerBuilder {

    private static String SERVER_IP = "127.0.0.1";
    private static int PORT = 10017;

    public static void start() throws IOException {
        Sys.p("mosheh Server started !!!");

        MoshehServer moshehServer = new MoshehServer(SERVER_IP, PORT);
        try {
            moshehServer.start();
        } catch (IOException e) {
            moshehServer.stop();
            throw e;
        }

        try {
            moshehServer.join();
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
    }
}
