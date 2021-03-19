package com.syx.springboot.threespringboot.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
    * 当客户端连接服务器完成就会触发该方法
     * @Author Song YuXin
     * @Date 17:21 2021/3/13
     * @Description //TODO 
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("客户端连接通道建立完成");
    }


    /**
    *  读取客户端发送的数据
     * @Author Song YuXin
     * @Date 17:30 2021/3/13
     * @Description //TODO 
     * @Param [ctx,上下文对象，含有通道chhannel，管道pipeline
     *         msg  就是客户端发送的数据
     * @return void
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到客户端的消息:"+ buf.toString(CharsetUtil.UTF_8));
    }



}
