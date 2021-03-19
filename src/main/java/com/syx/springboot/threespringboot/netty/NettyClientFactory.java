package com.syx.springboot.threespringboot.netty;

import com.syx.springboot.threespringboot.client.Client;
import com.syx.springboot.threespringboot.client.NettyClient;
import com.syx.springboot.threespringboot.client.RemotingUrl;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("syx")
public class NettyClientFactory  extends AbstractClientFactory{

    /**
     * 共享IO线程
     **/
    public static final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public static final ByteBufAllocator byteBufAllocator;

    private String serverAddress;
    private List<RemotingUrl> remoteAddress = new ArrayList<>();

    static {
        workerGroup.setIoRatio(SystemPropertyUtil.getInt("ioratio", 100));

        if (SystemPropertyUtil.getBoolean("bytebuf.pool", true)) {
            byteBufAllocator = PooledByteBufAllocator.DEFAULT;
        } else {
            byteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
        }
    }

    //@PostConstruct
    public void initRemoteAddress() {
        if (StringUtils.isEmpty(serverAddress)) {
            logger.error("远程推送地址为空");
            return;
        }
        String[] ipAddresses = serverAddress.split(",");
        for (String ipAddress : ipAddresses) {
            String[] domainPort = ipAddress.split(":");
            RemotingUrl remotingUrl = new RemotingUrl(domainPort[0], Integer.parseInt(domainPort[1]));
            remoteAddress.add(remotingUrl);
        }
    }

    @Override
    protected Client createClient(String channelName, RemotingUrl url) throws Exception {
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(NettyClientFactory.workerGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, NettyClientFactory.byteBufAllocator)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                //消息编码器
                                .addLast("encoder", new MessageEncoder());
                    }

                });
        //设置连接超时时间
        int connectTimeout = url.getConnectionTimeout();
        if (connectTimeout < 1000) {
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        } else {
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
        }

        String targetIP = url.getDomain();
        int targetPort = url.getPort();
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(targetIP, targetPort));
        if (future.awaitUninterruptibly(connectTimeout) && future.isSuccess() && future.channel().isActive()) {
            Channel channel = future.channel();
            logger.info("==========>连接服务端成功，通道 :{}", channelName);
            Client client = new NettyClient(channelName, channel);

            return client;
        } else {
            future.cancel(true);
            future.channel().close();
            logger.error("==========>连接服务端超时");
            return null;
        }
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public List<RemotingUrl> getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(List<RemotingUrl> remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
