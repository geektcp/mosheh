package com.geektcp.common.mosheh.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author geektcp on 2018/11/25.
 */
public class NIOServer {

    //通道选择器
    private Selector selector;

    public NIOServer(){
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 函数功能：服务器端开始监听，看是否有客户端连接进来
     * */
    private void listen() throws IOException {
        System.out.println("server running....");
        while(true){
            // 当注册事件到达时，方法返回，否则该方法会一直阻塞
            selector.select();
            // 获得selector中选中的相的迭代器，选中的相为注册的事件
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> ite = set.iterator();
            while(ite.hasNext()){
                SelectionKey selectionKey = ite.next();
                // 删除已选的key 以防重负处理
                ite.remove();
                if(selectionKey.isAcceptable()){//如果有客户端连接进来
                    //先拿到这个SelectionKey里面的ServerSocketChannel。
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                    // 获得和客户端连接的通道
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("有客户端连接到服务器！！！");
                    //将此通道设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //服务器端向客户端发送数据
                    socketChannel.write(ByteBuffer.wrap(new String("hello client!").getBytes()));

                    //为了接收客户端发送过来的数据，需要将此通道绑定到选择器上，并为该通道注册读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if(selectionKey.isReadable()){ //客户端发送数据过来了
                    //先拿到这个SelectionKey里面的SocketChannel。
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                    //接收来自于客户端发送过来的数据
                    ByteBuffer buf = ByteBuffer.allocate(128);
                    socketChannel.read(buf);
                    byte[] receData = buf.array();
                    String msg = new String(receData).trim();

                    System.out.println("接收来自客户端的数据为："+msg);
                    buf.clear();

//            		int len = 0;
//                	while((len=socketChannel.read(buf))!=-1){
//
//                		byte[] receData = buf.array();
//                		String msg = new String(receData).trim();
//                		System.out.println("接收来自客户端的数据为："+msg);
//                		buf.clear();
//                	}

                    //服务器端向客户端发送"确定信息"

//                	buf.put("收到信息!!!".getBytes());
//                	while(buf.hasRemaining()){
//                		socketChannel.write(buf);
//                	}
                    socketChannel.write(ByteBuffer.wrap(new String("收到信息!!!").getBytes()));
                    //buf.clear();
                }
            }

        }

    }



    /*
     * 函数功能：初始化serverSocketChannel来监听指定的端口是否有新的TCP连接，
     * 并将serverSocketChannel注册到selector中
     * */
    private void init(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //serverSocketChannel监听指定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);//设置为非阻塞模式

			/*
			 * 将serverSocketChannel注册到selector中,并为该通道注册selectionKey.OP_ACCEPT事件
			 * 注册该事件后，当事件到达的时候，selector.select()会返回，  如果事件没有到达selector.select()会一直阻塞
			 * */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.init(9999);
        server.listen();
    }

}
