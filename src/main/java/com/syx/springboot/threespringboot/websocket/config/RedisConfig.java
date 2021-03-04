package com.syx.springboot.threespringboot.websocket.config;


import com.syx.springboot.threespringboot.websocket.server.single.RedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 *
 * redis发布订阅配置
 **/
@Configuration
public class RedisConfig {

    /**
     * 监听容器
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter tspListenerAdapter,
                                                   MessageListenerAdapter cemsListenerAdapter,
                                                   MessageListenerAdapter vocListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //添加消息监听
        container.addMessageListener(tspListenerAdapter,new PatternTopic("tsp:getList"));
        container.addMessageListener(cemsListenerAdapter,new PatternTopic("cems:getList"));
        container.addMessageListener(vocListenerAdapter,new PatternTopic("voc:getList"));
        return container;

    }
    /**
     * 绑定消息监听者和接收监听的方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter tspListenerAdapter(RedisReceiver receiver){
        return new MessageListenerAdapter(receiver,"tspDataReceive");
    }


    @Bean
    public MessageListenerAdapter cemsListenerAdapter(RedisReceiver receiver){
        return new MessageListenerAdapter(receiver,"cemsDataReceive");
    }

    @Bean
    public MessageListenerAdapter vocListenerAdapter(RedisReceiver receiver){
        return new MessageListenerAdapter(receiver,"vocDataReceive");
    }
}
