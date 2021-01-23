package com.syx.springboot.threespringboot.unit;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestLocalDateTime {


    @Test
    public  void  testTestLocaldateTime(){

        String startTime =
                LocalDateTime.now()
                        .minusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        System.out.println("startTime："+startTime);
        System.out.println("endTime："+endTime);

        String stampStartTime  = dateToStamp(startTime);
        String stampEndTime  = dateToStamp(endTime);

        System.out.println("stampStartTime："+stampStartTime);
        System.out.println("stampEndTime："+stampEndTime);

        /**
         * startTime：2021-01-22 11:25:30
         * endTime：2021-01-23 11:25:31
         *
         *
         * stampStartTime：1611285930000000
         * stampEndTime：1611372331000000
         */



    }


    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts) + "000";
        return res;
    }
}
