package com.geektcp.common.mosheh.socket;

import com.geektcp.common.mosheh.socket.buffer.DataOutputBuffer;
import com.geektcp.common.mosheh.socket.text.ConnectionBody;
import com.geektcp.common.mosheh.socket.text.ConnectionHeader;
import com.geektcp.common.mosheh.system.Sys;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


/**
 * @author geektcp on 2023/2/2 1:04.
 */
public class MoshehClientBuilder {

    private static String SERVER_IP = "127.0.0.1";
    private static int PORT = 10017;
    private static Socket socket;

    public static void main(String[] args) {
        try {
            while (true) {
                try {
                    socket = new Socket();
                    SocketAddress remoteAddr = new InetSocketAddress(SERVER_IP, PORT);
                    socket.connect(remoteAddr, 60000);
                    socket.setTcpNoDelay(false);
                    break;
                } catch (IOException ie) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    socket = null;
                    Sys.sleep(1000);
                }
            }

            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));

            DataOutputBuffer buf = new DataOutputBuffer();
            ConnectionHeader header = new ConnectionHeader("TLV");
            header.write(buf);

            int bufLen = buf.getLength();
            out.writeInt(bufLen);
            out.write(buf.getData(), 0, bufLen);
            out.flush();

            DataOutputBuffer buf2 = new DataOutputBuffer();
            ConnectionBody writeBody = new ConnectionBody("are you ok!!!");
            writeBody.write(buf2);

            bufLen = buf2.getLength();
            out.writeInt(bufLen);
            out.write(buf2.getData(), 0, bufLen);
            out.flush();

            ConnectionBody readBody = new ConnectionBody();
            readBody.readFields(in);
            out.close();
            in.close();
            socket.close();
            Sys.p("test over!!!");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
