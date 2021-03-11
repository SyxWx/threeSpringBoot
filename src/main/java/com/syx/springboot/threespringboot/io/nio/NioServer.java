package com.syx.springboot.threespringboot.io.nio;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
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
        //创建NIO ServerSocketChannel   与 BIO的ServerSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务器启动成功");

        while(true){
            //非阻塞模式的accept方法不会阻塞，否则会阻塞
            //NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
            SocketChannel socketChannel  = serverSocketChannel.accept();
            if(socketChannel != null){//如果有客户端连接
                System.out.println("连接成功");
                //设置SocketChannel为非阻塞
                socketChannel.configureBlocking(false);
                //保存客户端信息连接再List中
                channelList.add(socketChannel);
            }
            //遍历连接经行数据读取
            Iterator<SocketChannel> iterator = channelList.iterator();
            while(iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                //非阻塞模式，read方法不会阻塞，否则胡阻塞
                int len = sc.read(byteBuffer);
                //如果有数据，则把数据打印出来
                if(len>0){
                    System.out.println("接受到的消息："+new String(byteBuffer.array()));
                }else if(len == -1){//如果客户端断开，把socket从集合中去除掉
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }
        }
    }
}
