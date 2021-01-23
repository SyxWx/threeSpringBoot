package com.syx.springboot.threespringboot.netty;

import com.syx.springboot.threespringboot.client.Client;
import com.syx.springboot.threespringboot.client.RemotingUrl;

import java.util.List;

/**
 *  接口描述：长连接客户端工厂类
 */
public interface ClientFactory {
    /**
     * 查询和新增
     * @param sn
     * @param url
     * @return
     * @throws Exception
     */
    Client get(String sn, RemotingUrl url);

    /**
     * 查询
     * @return
     * @throws Exception
     */
    List<Client> getAllClients() throws Exception;

    /**
     * 定时
     */
    void checkAllChannelHeart();

}
