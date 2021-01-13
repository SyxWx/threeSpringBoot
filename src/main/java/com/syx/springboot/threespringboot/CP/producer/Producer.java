package com.syx.springboot.threespringboot.CP.producer;


import com.syx.springboot.threespringboot.middle.Middle;
import org.springframework.stereotype.Service;

/**
 * 发布订阅者模式——发布者
 *
 */
@Service
public interface Producer {


    /***
     * 写入队列
     * @param middle
     */
    void send(Middle middle);

    /***
     * 关闭写入
     */
    void forbiddenSend();


}
