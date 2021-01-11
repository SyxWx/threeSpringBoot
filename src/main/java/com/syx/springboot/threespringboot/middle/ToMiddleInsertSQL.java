package com.syx.springboot.threespringboot.middle;

import littlebee.excel.ExcelImport;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ToMiddleInsertSQL {

    @Test
    public void testToMiddleInserSQl() throws  Exception{

        List<Middle> list = new ArrayList<>();

        ExcelImport middleExcel = new ExcelImport(Middle.class,1,"D:\\BME\\middle模板.xlsx");

        list=middleExcel.getModelList(Middle.class);


        /**
         * StringBuffer  线程安全   慢
         * StringBuilder 非线程安全 快
         */
        StringBuilder sql   =  new StringBuilder();
        StringBuilder values   =  new StringBuilder();
        String value = "";
        /**
         * INSERT INTO data_types
         * (facility_title,facility_type_id,facility_category,plant_title,
         *  process_title,title,category_id,                -- ,type,period,format,
         *  unit,status,remark,device_no,node_id, is_opc)
         * VALUES
         *  ('1', '散料秤#1', NULL, '1', '球团厂', '1#2#竖炉', '流量', '2', '4', '60', '2', 't/h',  '1', '一分钟平均值', 'YG_SC_QT_01', NULL, '0')
         */
        for (Middle middle :  list){
            values.append("(");
            // 设备名称    facility_title;
            values.append( middle.getFacility_title()!= null && !middle.getFacility_title().isEmpty() ? "'"+middle.getFacility_title() +"',":"NULL,");
            // CEMS 类别  1539730750575002 facility_type_id;
            if("3".equals(middle.getFacility_category())){
                values.append( "1539730750575002,");
            }else{
                values.append( "NULL,");
            }
            // 类别 facility_category;
            values.append( middle.getFacility_category()!= null && !middle.getFacility_category().isEmpty() ? "'"+middle.getFacility_category() +"',":"NULL,");
            // 分厂所属区域   plant_title;
            values.append( middle.getPlant_title()!= null && !middle.getPlant_title().isEmpty() ? "'"+middle.getPlant_title() +"',":"NULL,");
            // 生产工艺线   process_title;
            values.append( middle.getProcess_title()!= null && !middle.getProcess_title().isEmpty() ? "'"+middle.getProcess_title() +"',":"NULL,");
            // 信号名称   title;
            values.append( middle.getTitle()!= null && !middle.getTitle().isEmpty() ? "'"+middle.getTitle() +"',":"NULL,");
            // 信号类型   category_id;
            values.append( middle.getCategory_id()!= null && !middle.getCategory_id().isEmpty() ? "'"+middle.getCategory_id() +"',":"NULL,");
            // 单位   unit;
            values.append( middle.getUnit()!= null && !middle.getUnit().isEmpty() ? "'"+middle.getUnit() +"',":"NULL,");
            //状态 1 0 status;
            values.append( "8,");
            // 频率   remark;
            values.append( middle.getRemark()!= null && !middle.getRemark().isEmpty() ? "'"+middle.getRemark() +"',":"NULL,");
            // 设备号   device_no;
            values.append( middle.getDevice_no()!= null && !middle.getDevice_no().isEmpty() ? "'"+middle.getDevice_no() +"',":"NULL,");
            // node_id;  采集OPC
            values.append( middle.getNode_id()!= null && !middle.getNode_id().isEmpty() ? "'"+middle.getNode_id() +"',":"NULL,");
            // 是否为opc采集 1 0 is_opc;
            if(middle.getNode_id()!= null && !middle.getNode_id().isEmpty()){
                values.append("1");
            }else{
                values.append( "0");
            }
            //取值类型  1一次  2最小值  3最大值  4平均值  5累计次数g type;
            //取值频率 秒  30  60 300  period;
            //数据类型 1 int   2  float 3  bool   4 string format;
            values.append("),");
        }
        sql.append("insert data_types ( "
                + " facility_title,facility_type_id,facility_category,plant_title, "
                + " process_title,title,category_id, "                                    //type,period,format,
                + " unit,status,remark,device_no,node_id, is_opc ) "
                + " values "
                );
        sql.append(values);



        System.out.println(list.toString());

        System.out.println("----------------------------------------------------------");
        System.out.println(sql);
        System.out.println("----------------------------------------------------------");




    }
}