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
        String type= "";
        String period = "";
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
            //取值类型  1一次  2最小值  3最大值  4平均值  5累计次数
            type = getTypeByRemark(middle.getRemark());
            values.append( type!=null&&!type.isEmpty()  ? "'"+type +"',":"NULL,");

            period = getPeriodByRemark(middle.getRemark());
            values.append( period!=null&&!period.isEmpty()  ? "'"+period +"',":"NULL,");
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
            //取值频率 秒  30  60 300  period;
            //数据类型 1 int   2  float 3  bool   4 string format;
            values.append("),");
        }
        sql.append("insert data_types ( "
                + " facility_title,facility_type_id,facility_category,plant_title, "
                + " process_title,title,category_id, "                                    //type,period,format,
                + " unit,status,remark,type,period,device_no,node_id, is_opc ) "
                + " values "
                );
        sql.append(values);

        System.out.println("---------------------新增条数："+list.size() +" 条 -------------------------------------");
        System.out.println(sql);
        System.out.println("----------------------------------------------------------");


        /**
         *
         -- 去重
         SELECT * FROM
         (SELECT device_no,title,COUNT(1) ct FROM data_types GROUP BY device_no,title) t WHERE ct>1

         -- 更新type
         UPDATE data_types SET type=5 WHERE remark LIKE '%累计次数%' AND type IS NULL
         UPDATE data_types SET type=4 WHERE remark LIKE '%平均值%' AND type IS NULL
         UPDATE data_types SET type=3 WHERE remark LIKE '%最大值%' AND type IS NULL
         UPDATE data_types SET type=2 WHERE remark LIKE '%最小值%' AND type IS NULL
         UPDATE data_types SET type=1 WHERE remark LIKE '%一次%' AND type IS NULL

         UPDATE data_types SET status=1 where  status = 8;

         -- 更新period
         UPDATE data_types SET period=30 WHERE remark LIKE '30%' AND period IS NULL
         UPDATE data_types SET period=60 WHERE (remark LIKE '一分钟%' OR remark LIKE '1分钟%') AND period IS NULL
         UPDATE data_types SET period=300 WHERE (remark LIKE '5分钟%' OR remark LIKE '五分钟%') AND period IS NULL

         -- 更新format
         -- format=2   2:float
         UPDATE data_types SET format=2 WHERE (category_id IN (2,3,6,9,11,12,17,18,19,21,22,23,70,113,14,8,10,20,15,'300','301','302','303')) AND format IS NULL
         -- format=1  1:int
         UPDATE data_types SET format=1 WHERE category_id=1 AND format IS NULL
         -- format=3  3:bool（true:1,false:0)，
         UPDATE data_types SET format=3 WHERE category_id=30 AND format IS NULL
         -- format=4  4:string
         UPDATE data_types SET format=4 WHERE category_id IN (4,5,24) AND format IS NULL
         *
         */


    }

    //返回类型 //取值类型  1一次  2最小值  3最大值  4平均值  5累计次数
    public String getTypeByRemark(String remark){
        if(remark!=null && !remark.isEmpty()){
            if(remark.indexOf("一次")>-1){
                return "1";
            }else if(remark.indexOf("最小值")>-1){
                return "2";
            }else if(remark.indexOf("最大值")>-1){
                return "3";
            }else if(remark.indexOf("平均值")>-1){
                return "4";
            }else if(remark.indexOf("累计次数")>-1){
                return "5";
            }else{
                return null;
            }
        }
        return null;
    }

    //返回类型 //取值类型  1一次  2最小值  3最大值  4平均值  5累计次数
    public String getPeriodByRemark(String remark){
        if(remark!=null && !remark.isEmpty()){
            if(remark.indexOf("30")>-1){
                return "30";
            }else if(remark.indexOf("一分钟")>-1 || remark.indexOf("1分钟")>-1 || remark.indexOf("60")>-1){
                return "60";
            }else if(remark.indexOf("5分钟")>-1 || remark.indexOf("五分钟")>-1){
                return "300";
            }else{
                return null;
            }
        }
        return null;
    }
}
