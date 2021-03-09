package com.syx.springboot.threespringboot.io.nio;



import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/*
*
 * @Author Song YuXin
 * @Date 18:31 2021/3/9
 * @Description //TODO
 **/
public class NioServer {

    //保存客户连接数量
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        //serverSocket.socket().bind();

    }
}
