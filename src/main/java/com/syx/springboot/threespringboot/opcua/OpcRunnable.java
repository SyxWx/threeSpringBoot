package com.syx.springboot.threespringboot.opcua;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



@Slf4j
@Setter
@Getter
public class OpcRunnable implements Runnable {

    private int period;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ConcurrentHashMap<String, Integer> collectMap = new ConcurrentHashMap<>();


    @Override
    public void run() {
        while (true) {
            try {
                log.info("==========>OPC Thread {} Running", Thread.currentThread().getName());
                this.loadDataTypes();
            } catch (Exception e) {
                log.info("==========>OPC Thread {} Occurred Error,Detail: {}", Thread.currentThread().getName(), e);
            } finally {
                try {
                    Thread.sleep(this.period * 100);
                } catch (InterruptedException e) {
                    log.error("线程{}sleep失败", this.period);
                }
            }
        }

    }

    private void loadDataTypes() {
        log.info("==========>发送CEMS数据开始");
    }
}


