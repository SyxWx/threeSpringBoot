package com.syx.springboot.threespringboot.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LagrangeList {


    public static List<Map<String,Object>> BELIE(List<Map<String,Object>> findTdcList) {
        //原始集合判断处理
        //起始时间,结束时间     <20次 中位数插值  >20次无数据不插值
        //数据中间断点 <20次拉格朗日      >20次 无数据不插值
        //数据集为负数 插值>0 修正为负数   数据集为正 插值<0 修正为正
        int  listNullCount=0;
        List<Map<String,Object>>  countList =new ArrayList<>();//便利集合
        List<Map<String,Object>> findOther=new ArrayList<>();//保存不变得数据
        for(int ic=0;ic<findTdcList.size();ic++){
            if (findTdcList.get(ic).get("time") == null) {
                countList.add(findTdcList.get(ic));
                listNullCount++;
                if( ic>20 && listNullCount>20) {
                    countList=aa(countList); // 首尾 大于20    无数据不插值
                }
                if(ic==findTdcList.size()-1){
                    findOther.addAll(countList);
                }
            } else  {
                if(countList==null){
                    findOther.add(findTdcList.get(ic));
                }else {
                    findOther.addAll(countList);
                    findOther.add(findTdcList.get(ic));
                }
                countList=new ArrayList<>();
                listNullCount=0;
            }
        }
        findTdcList=findOther;
        int indexOne = 0;// todo 这个是赋值时间数据长度以及值数据长度
        int indexTow = 0;// todo 这个是赋值需要插入长度
        //todo 这一步只是设置数据长度
        for (int ii = 0; ii < findTdcList.size(); ii++) {
            if (findTdcList.get(ii).get("time") != null && findTdcList.get(ii).get("value") != null) {
                indexOne++;
            } else {
                indexTow++;
            }
        }
        double x[] = new double[indexOne];
        double y[] = new double[indexOne];
        double x0[] = new double[indexTow];
        double y0[] = new double[indexTow];
        int lastIndexOne = 0;// todo 这个是数据里没一个下标
        int lastIndexTow = 0;// todo 这个是数据里没一个下标
        for (int ii = 0; ii < findTdcList.size(); ii++) {
            // todo 当查询出数据库有值时候，则赋值到x轴去
            if (findTdcList.get(ii).get("time") != null && findTdcList.get(ii).get("value") != null) {
                String val = findTdcList.get(ii).get("value") == null ? "0" : findTdcList.get(ii).get("value").toString();
                Double value = Double.parseDouble(val);
                y[lastIndexOne] = value;
                x[lastIndexOne] = ii;
                lastIndexOne++;
            } else {
                x0[lastIndexTow] = ii;
                lastIndexTow++;
            }
        }
        int MaxIndex=0;
        Map<Integer, Double> mapXy = new HashMap<>();
        for (int ap = 0; ap < x.length; ap++) {
            int da = new Double(x[ap]).intValue();
            mapXy.put(da, y[ap]);
            if(ap==x.length-1){
                MaxIndex=da;
            }
        }
        //todo 当需要插值过多是会出现龙格现象。 处理需要遍历集合
        int length = 4;
        for (int ix = 0; ix < x0.length; ix++) {
            int value = new Double(x0[ix]).intValue();// 求出的其实就是x0集合的下标
            int valueLen = x.length;//求出入参x集合长度
            if (valueLen >= length) {
                double xNew[] = new double[length];
                double yNew[] = new double[length];
                double x0New[] = new double[1];
                if (value <= x[0]) {//当为第一个值时为0时，像后取4位
                    xNew[0] = x[0];
                    yNew[0] = y[0];
                    xNew[1] = x[1];
                    yNew[1] = y[1];
                    xNew[2] = x[2];
                    yNew[2] = y[2];
                    xNew[3] = x[3];
                    yNew[3] = y[3];
                } else if (value <= x[0]+1) {
                    xNew[0] = x[0];
                    yNew[0] = y[0];
                    xNew[1] = x[2];
                    yNew[1] = y[2];
                    xNew[2] = x[3];
                    yNew[2] = y[3];
                    xNew[3] = x[4];
                    yNew[3] = y[4];
                } else if (value + 2 >= (valueLen+x0.length)) {
                    xNew[0] = x[valueLen - 4];
                    yNew[0] = y[valueLen - 4];
                    xNew[1] = x[valueLen - 3];
                    yNew[1] = y[valueLen - 3];
                    xNew[2] = x[valueLen - 2];
                    yNew[2] = y[valueLen - 2];
                    xNew[3] = x[valueLen - 1];
                    yNew[3] = y[valueLen - 1];
                } else {
                    int valueFu = 0;
                    test:
                    while (true) {
                        if (mapXy.containsKey(value - valueFu)) {
                            xNew[0] = value - valueFu;
                            yNew[0] = mapXy.get(value - valueFu);
                            break test;
                        }
                        if(valueFu>MaxIndex){
                            xNew[0] =  valueLen-4;
                            yNew[0] = y[valueLen-4];
                            break test;
                        }
                        valueFu++;
                    }
                    valueFu++;
                    test:
                    while (true) {
                        if (mapXy.containsKey(value - valueFu)) {
                            xNew[1] = value - valueFu;
                            yNew[1] = mapXy.get(value - valueFu);
                            break test;
                        }
                        if(valueFu>MaxIndex){
                            xNew[1] =  valueLen-3;
                            yNew[1] = y[valueLen-3];
                            break test;
                        }
                        valueFu++;
                    }
                    int valueZh = 1;
                    test:
                    while (true) {
                        if (mapXy.containsKey(value + valueZh)) {
                            xNew[2] = value + valueZh;
                            try {
                                yNew[2] = mapXy.get(value + valueZh) == null ? 0.0 : mapXy.get(value + valueZh);
                            } catch (Exception e) {
                                yNew[2] = 0;
                            }
                            break test;
                        }
                        if(valueZh>MaxIndex){
                            xNew[2] =   valueLen-2;
                            yNew[2] =   y[valueLen-2];
                            break test;
                        }
                        valueZh++;
                    }
                    valueZh++;
                    test:
                    while (true) {
                        if (mapXy.containsKey(value + valueZh)) {
                            xNew[3] = value + valueZh;
                            try {
                                yNew[3] = mapXy.get(value + valueZh) == null ? 0.0 : mapXy.get(value + valueZh);
                            } catch (Exception e) {
                                yNew[3] = 0;
                            }
                            break test;
                        }
                        if(valueZh>MaxIndex){
                            xNew[3] =  valueLen-1;
                            yNew[3] = y[valueLen-1];
                            break test;
                        }
                        valueZh++;
                    }
                }
                x0New[0] = value;
                //判断插入后的值  大于或者小于 当前入参数据组时  去数组内的值
                Double valueY= Lagrange.BELIE(xNew, yNew, x0New)[0];
                Double maxIndex = yNew[0];//定义最大值为该数组的第一个数
                Double minIndex = yNew[0];//定义最小值为该数组的第一个数
                for (int i = 0; i < yNew.length; i++) {
                    if(maxIndex < yNew[i]){
                        maxIndex = yNew[i];
                    }
                    if(minIndex > yNew[i]){
                        minIndex = yNew[i];
                    }
                }
                if(valueY>maxIndex){
                    y0[ix] =maxIndex;
                }else if (valueY<minIndex){
                    y0[ix] =minIndex;
                }else {
                    y0[ix] =valueY;
                }
            }
        }
        int valueIndex = 0;
        for (int ii = 0; ii < findTdcList.size(); ii++) {
            if (findTdcList.get(ii).get("time") == null) {
                findTdcList.get(ii).put("time", findTdcList.get(ii).get("dataTime"));
                findTdcList.get(ii).put("value", y0[valueIndex]);
                valueIndex++;
            }
        }
        return  findTdcList;
    }
    public  static   List<Map<String,Object>>  aa(  List<Map<String,Object>> a ){
        for(int ao=0;ao<a.size();ao++){
            if (a.get(ao).get("time") == null) {
                a.get(ao).put("dataTime", a.get(ao).get("dataTime"));
                a.get(ao).put("time", a.get(ao).get("dataTime"));
                a.get(ao).put("value",null);
            }
        }
        return a;
    }
}
