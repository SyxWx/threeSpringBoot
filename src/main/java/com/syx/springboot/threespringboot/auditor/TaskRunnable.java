package com.syx.springboot.threespringboot.auditor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yutyi
 * @date 2020/07/03
 */
@Slf4j
@Getter
@Setter
public class TaskRunnable implements Runnable {

    private int period;
    private AtomicLong sendCount = new AtomicLong(0);

    @Override
    public void run() {

        while (true) {
            try {
                log.info("==========>Thread {} Running", Thread.currentThread().getName());
                this.loadDataTypes(this.period);
            } catch (Exception e) {
                log.info("==========>Thread {} Occurred Error,Detail: {}", Thread.currentThread().getName(), e);
            } finally {
                try {
                    Thread.sleep(this.period * 1000);
                } catch (InterruptedException e) {
                    log.error("线程{}sleep失败", this.period);
                }
            }

        }

    }


    private void loadDataTypes(Integer period) {
        Date fromDate = new Date(System.currentTimeMillis() - (period + 240) * 1000);
      //  Date fromDateEnd = new Date(System.currentTimeMillis() - 10 *1000);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("====FROM_DATETIME>>>>SQL查询,createTime条件的值：{}", sdf.format(fromDate));
        System.out.println(period+"  where createtime > ' "+sdf.format(fromDate));

      //  log.info("====FROM_DATETIME>>>>SQL查询,CEMS查询createTime条件的值：{}", sdf.format(fromDateEnd));
     //   System.out.println(" where createtime <  ' "+sdf.format(fromDateEnd)+" ' and createtime > ' "+sdf.format(fromDate));
     //   System.out.println(" where createtime <  ' "+sdf.format(fromDateEnd)+" ' and createtime > ' "+sdf.format(fromDate)+" '-------------"+ sdf.format(fromDate)+""+sdf.format(fromDateEnd));


    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }


}
