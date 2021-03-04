package com.syx.springboot.threespringboot.websocket.server.single;

import org.springframework.stereotype.Service;

/**
 * *  redis消息接收处理
 */
@Service
public class RedisReceiver {


    /**
     * tsp数据接收处理
     */
    public void tspDataReceive(String message) {
        SingleWebSocketServer.sendMessage(message,"tsp:getList");
    }

    /**
     * cems数据接收处理
     */
    public void cemsDataReceive(String message) {
       // WsHandler.sendMessage(message,"cems:getList");
    }

    /**
     * voc数据接收处理
     */
    public void vocDataReceive(String message) {
       // NioWebSocketHandler.sendMessage(message,"voc:getList");
    }

}
