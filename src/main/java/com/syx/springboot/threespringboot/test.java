package com.syx.springboot.threespringboot;

import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class test {

    private ThreadLocal<List> threadLocal = new ThreadLocal<>();

   @Test
    public void getSupplementaryData() {
     //  http://localhost:9100/supplementary?startTime=2020-01-19 19:28:59&endTime=2020-01-25 19:28:59

      String startTime1 = "2020-01-19 19:28:59";
       String endTime1 ="20200-12-5 19:28:59";
       System.out.println(startTime1 +"--------"+ endTime1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = format.parse(startTime1);
            Date endDate = format.parse(endTime1);

            System.out.println(startDate +"--------"+ endDate);
        } catch (ParseException e) {
            e.printStackTrace();

        }

       threadLocal.set(Arrays.asList(startTime1, endTime1, 30));

       Long startTime = (Long) threadLocal.get().get(0);
       Long endTime = startTime + 30 * 1000;

       //更新开始时间
       threadLocal.get().set(0, endTime);


       System.out.println(startTime +"--------"+ endTime);


       //数据结束时间限制
       Long limitTime = (Long) threadLocal.get().get(1);

    }
}
