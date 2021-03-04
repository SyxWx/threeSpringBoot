package com.syx.springboot.threespringboot.websocket.controller;


import com.syx.springboot.threespringboot.websocket.common.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/ws")
public class WebSocketController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public MessageResult<Object> put(String message, @PathVariable("topic") String topic){
        redisTemplate.convertAndSend(topic,message);
        return MessageResult.success();
    }


}
