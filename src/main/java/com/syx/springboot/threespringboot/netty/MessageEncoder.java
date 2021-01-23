package com.syx.springboot.threespringboot.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<PackageData> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PackageData packageData, ByteBuf byteBuf) throws Exception {

    }


}
