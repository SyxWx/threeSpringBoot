package com.syx.springboot.threespringboot.CP;

import com.syx.springboot.threespringboot.CP.consumer.Consumer;
import com.syx.springboot.threespringboot.CP.consumer.ConsumerImpl;
import com.syx.springboot.threespringboot.CP.producer.Producer;
import com.syx.springboot.threespringboot.CP.producer.ProducerImpl;
import com.syx.springboot.threespringboot.middle.Middle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
@Controller
@EnableScheduling
public class TestCP {

    private Producer producer;
    private Consumer consumer;
    private BlockingQueue<Middle> queue;

    @Scheduled(cron = "1/3 0 0 * * ?")
    @Test
    public void testPC(){
        System.out.println("|-----------测试生产者消费者-------------|");
        queue = new ArrayBlockingQueue<>(50);
        this.producer = new ProducerImpl(queue);
        this.consumer = new ConsumerImpl(queue);
        //消费者线程池
        this.consumer.start();

        for (int i=1;i<23;i++){
            //System.out.println("|-----------  Middle  -------------|");
            Middle middle = new Middle();
            middle.setFacility_title("名称:--"+i+"--");
            //System.out.println("|-----------  "+i+"  -------------|");
            producer.send(middle);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------ok--------------------------");


        for (int i=1;i<18;i++){
            //System.out.println("|-----------  Middle  -------------|");
            Middle middle = new Middle();
            middle.setFacility_title("名称:--"+i+"--");
            //System.out.println("|-----------  "+i+"  -------------|");
            producer.send(middle);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------ok--------------------------");

    }
}
