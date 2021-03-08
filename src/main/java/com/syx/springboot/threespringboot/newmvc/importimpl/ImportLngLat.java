package com.syx.springboot.threespringboot.newmvc.importimpl;

import com.syx.springboot.threespringboot.newmvc.bean.EmissionSourceBean;
import com.syx.springboot.threespringboot.newmvc.bean.Lnglat;
import littlebee.excel.ExcelImport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ImportLngLat {


    /*
     *
     * @Author Song YuXin
     * @Date 14:29 2021/3/4
     * @Description //TODO
     * @Param []
     * @return void
     **/
    @Test
    public void testToLngLatInserSQl() throws  Exception {

        List<Lnglat> list = new ArrayList<>();

        ExcelImport lnglatExcel = new ExcelImport(Lnglat.class, 1, "D:\\BMENEW\\Lnglat模板.xlsx");

        list = lnglatExcel.getModelList(Lnglat.class);

        /**
         * StringBuffer  线程安全   慢
         * StringBuilder 非线程安全 快
         */
        StringBuilder updatesql = new StringBuilder();
        String lng = "";
        String lat = "";
        String lnglat = "";
        int sum = 0;
        for (Lnglat Ldata : list) {
            StringBuilder sql = new StringBuilder();
            StringBuilder values = new StringBuilder();
            StringBuilder where = new StringBuilder();
            // "客户ID"
            where.append(" customer_id = '"+Ldata.getCustomerid()+"' ");
            // "device_no"
            where.append(" and device_no = '"+Ldata.getDevice_no()+"' ");
            // "经纬度坐标",
            lnglat = Ldata.getLnglat();
            String[] strs = lnglat.split(";");
            if(lnglat!=null && !lnglat.isEmpty()) {
                if (strs.length > 1) {
                    values.append(" longitude = '" + strs[0] + "' ");
                    values.append(" , latitude = '" + strs[1] + "' ");
                } else {


                }
                sum++;
            }else{
                return;
            }
            sql.append("update  t_device  set ");
            sql.append(values);
            sql.append(" where  ");
            sql.append(where);
            updatesql.append(sql+";");
        }

        System.out.println("-------------总条数：" + list.size() + " 条，修改条数 ："+ sum +" -------------");
        System.out.println(updatesql);
        System.out.println("----------------------------------------------------------");
    }


    @Test
    public void test() throws  Exception {

        String s = "118.56225241476727;39.96744398346829";


        int len = s.length();

        System.out.println(len);

        String one = s.substring(0,len/2);
        System.out.println(one);
        String two = s.substring((len/2+1),len);
        System.out.println(two);

    }

}
