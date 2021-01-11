package com.syx.springboot.threespringboot.opcua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@EnableScheduling
public class opctest {


    /**
     * 收集数据集合
     * 100
     * 35
     * 65 * 3
     * 195W
     *
     */
    private ConcurrentHashMap<String, Integer> collectMap = new ConcurrentHashMap<>();

    //
    public static List<OpcRunnable> opcRunnableList = new ArrayList<>();


    @PostConstruct
    private void initialize() {
        //订阅
        this.subscribe();
        //收集
        this.collect();
    }

    @Scheduled(cron = "1/3 * * * * ?")
    public String subscribe() {
        //System.out.println("初始化--subscribe -订阅开始-----------------");
        log.info("初始化--subscribe -订阅开始-----------------");
        return "订阅失败";
    }


    public void collect() {
        System.out.println("初始化--collect -收集开始");

        List<Integer> opcThreadNum = Arrays.asList(30,500);
        log.info("================> start OPC collect，ThreadNum：{}", opcThreadNum);
        if (opcThreadNum != null && opcThreadNum.size() > 0) {
            //启动多个线程
            for (Integer period : opcThreadNum) {
                    OpcRunnable opcRunnable = new OpcRunnable();
                    opcRunnable.setPeriod(period.intValue());
                    opcRunnable.setCollectMap(collectMap);
                    new Thread(opcRunnable, "OPC thread" + opcRunnable.getPeriod()).start();
                    opcRunnableList.add(opcRunnable);
                    log.info("==========>start OPC thread: {}", opcRunnable.getPeriod());

            }
        }
    }

}
