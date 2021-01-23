package com.syx.springboot.threespringboot.netty;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.syx.springboot.threespringboot.client.Client;
import com.syx.springboot.threespringboot.client.RemotingUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class AbstractClientFactory implements ClientFactory {
    protected Logger logger = LoggerFactory.getLogger(AbstractClientFactory.class);
    /**
     * 长连接缓存，一个channelName对应一个长连接
     */
    public final Cache<String, Client> cacheClients = CacheBuilder.newBuilder()
            // 单机长连接上限，超过上限采用LRU淘汰
            .maximumSize(65535)
            // 连接长时间没有读写则删除
            .expireAfterAccess(300, TimeUnit.SECONDS)
            .removalListener((RemovalListener<String, Client>) notification -> {
                // 关闭连接
                logger.info("==========>通道{}连接心跳超时断开", notification.getValue().getChannelName());
                notification.getValue().invalidate();
            })
            .build();

    /**
     * 获取客户端
     *
     * @param channelName
     * @param domain
     * @return
     * @throws Exception
     */
    protected abstract Client createClient(String channelName, RemotingUrl domain) throws Exception;

    @Override
    public Client get(String channelName, RemotingUrl url) {
        try {
            Client client = cacheClients.get(channelName, new Callable<Client>() {

                @Override
                public Client call() throws Exception {
                    return createClient(channelName, url);
                }
            });
            if (!client.isConnected()) {
                cacheClients.invalidate(channelName);
                logger.warn("==========>通道{}连接断开", channelName);
                client = get(channelName, url);
            }
            return client;
        } catch (Exception e) {
            logger.error("==========>获取通道{}失败", channelName);
        }
        return null;

    }

    @Override
    public List<Client> getAllClients() throws Exception {
        List<Client> result = new ArrayList<Client>((int) cacheClients.size());
        result.addAll(cacheClients.asMap().values());
        return result;
    }

    /**
     * 检查所有通道心跳
     */
    @Override
    public void checkAllChannelHeart() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cacheClients.cleanUp();
            }
        }, 10000, 30000);
    }

}
