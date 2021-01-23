package com.syx.springboot.threespringboot.client;

public interface Client {
    /**
     * 启动心跳检测
     * @throws Exception
     */
    void startHeartBeat() throws Exception;

    /**
     * 发送请求
     * @param msg
     * @throws Exception
     */
    void sendRequest(Object msg) throws Exception;

    /**
     * 获取通道名称
     * @return
     */
    String getChannelName();

    /**
     * 通道是否保活
     * @return
     */
    boolean isConnected();

    /**
     * 失效通道
     */
    void invalidate();
}
