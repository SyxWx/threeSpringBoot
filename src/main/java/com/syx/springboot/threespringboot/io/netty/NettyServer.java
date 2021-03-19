package com.syx.springboot.threespringboot.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

        public static void main(String[] args) throws  Exception{

            //创建2个线程组bossGroup和workerGroup，含有的子线程NioEventLoop的个数，默认为cpu核数的2倍
            //bossGroup只是处理连接请求，真正和客户端业务处理，会交给workerGroup完成
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup(9);

            //创建服务器端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来配置参数
            bootstrap.group(bossGroup,workerGroup)//设置两个线程
                    //使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //初始化服务器连接队列大小，服务端处理客户端来接请求是顺序处理的，所有同一时间只能处理一个客户端连接
                    // 多个客户端同时来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建通道初始化对象，设置初始化参数，在SocketChannel
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //对workerGroup的SocketChannel设置处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());

                        }
                    });
            System.out.println("netty server start。。。。");
            //绑定一个端口并且同步，生成一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            //启动服务器，并且绑定端口，bind是一个异步操作，sync()方法是等待异步操作执行完毕。
            ChannelFuture cf = bootstrap.bind(9000).sync();

            //等待服务器监听端口关闭，closeFuture是异步操作
            //通过sync()方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
            cf.channel().closeFuture().sync();

    }


}
