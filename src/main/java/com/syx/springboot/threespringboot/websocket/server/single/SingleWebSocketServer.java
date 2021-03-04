package com.syx.springboot.threespringboot.websocket.server.single;


import com.alibaba.fastjson.JSON;
import com.syx.springboot.threespringboot.websocket.domain.MessageDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOError;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  *
 *  * @ServerEndpoint 将当前类定义为一个websocket服务器，客户端可以通过配置的URL来连接到WebSocket服务器端
 */
@Slf4j
@Data
@ServerEndpoint("/ws/magic/{type}/{method}/{customerId}")
@Component
public class SingleWebSocketServer {


    /**
     * 在线连接
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger();

    /**
     * 保存每个客户端对应的session
     */
    private static final ConcurrentHashMap<String,Session> SESSION_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        //初始化
        log.info("websocket初始化....");
    }

    /**
     * 关闭连接调用
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("type") String type,
                       @PathParam("method") String method,
                       @PathParam("customerId")Integer customerId)throws IOException{
        String key = type+":"+method+":"+customerId;
        if(SESSION_MAP.containsKey(key)) {
            SESSION_MAP.remove(key);
        }
        SESSION_MAP.put(key,session);
        int currentOnline = ONLINE_COUNT.incrementAndGet();
        log.info("{}连接成功",customerId);
        log.info("当前在线连接数：{}",currentOnline);
        session.getBasicRemote().sendText("连接成功！");
    }

    /**
     * 关闭连接调用
     */
    @OnClose
    public void onClose(@PathParam("type")String type,
                        @PathParam("method")String method,
                        @PathParam("customerId")Integer customerId){
        String key=type+"："+method+":"+customerId;
        if(!SESSION_MAP.containsKey(key)){
            return;
        }
        SESSION_MAP.remove(key);
        int currentOnline = ONLINE_COUNT.decrementAndGet();
        log.info("{}下线",customerId);
        log.info("当前连接数:{}",currentOnline);
    }

    /**
     *
     *  接收客户端消息
     */
    @OnMessage
    public void onMessage(String message,@PathParam("customerId")Integer customerId){
        log.info("收到客户端{}消息:{}",customerId,message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session,Throwable error){
        log.error("发生错误",error);
    }



    /**
     * 服务端消息推送指定websocket客户端
     */
    public static void sendMessage(String message,String topic){
        MessageDTO messageDTO = JSON.parseObject(message,MessageDTO.class);
        String sessionId  = topic+":"+messageDTO.getCustomerId();
        if(StringUtils.isBlank(sessionId) || !SESSION_MAP.containsKey(sessionId)){
            log.error("{}不在线！！",topic);
            return;
        }
        try {
            SESSION_MAP.get(sessionId).getBasicRemote().sendText(messageDTO.getData());
        } catch (IOException e) {
            log.error("推送消息失败，customerId:{}",message,e);
        }

    }
}
