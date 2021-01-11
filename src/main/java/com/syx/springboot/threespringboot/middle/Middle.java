package com.syx.springboot.threespringboot.middle;


import io.swagger.annotations.ApiModelProperty;
import littlebee.excel.ExcelField;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 *
 * 300	PM2.5	PM2.5微站 format2
 * 301	PM10	PM10 微站 format2
 * 302	TSP	TSP 微站  format2
 * 303	方向	方向 微站  format2
 */
@Data
public class Middle {

    @ApiModelProperty(value = "类别", name = "facility_category", example = "类别")
    @ExcelField(sort = 1, required = false)
    private String facility_category;

    @ApiModelProperty(value = "分厂所属区域", name = "facility_title", example = "分厂所属区域")
    @ExcelField(sort = 2, required = false)
    private String plant_title;

    @ApiModelProperty(value = "生产工艺线", name = "process_title", example = "生产工艺线")
    @ExcelField(sort = 3, required = false)
    private String process_title;

    @ApiModelProperty(value = "设备号", name = "device_no", example = "设备号")
    @ExcelField(sort = 4, required = false)
    private String device_no;

    @ApiModelProperty(value = "设备名称", name = "facility_title", example = "设备名称")
    @ExcelField(sort = 5, required = false)
    private String facility_title;



    @ApiModelProperty(value = "信号类型", name = "category_id", example = "信号类型")
    @ExcelField(sort = 6, required = false)
    private String category_id;

    @ApiModelProperty(value = "信号名称", name = "title", example = "信号名称")
    @ExcelField(sort = 7, required = false)
    private String title;



    @ApiModelProperty(value = "单位", name = "unit", example = "单位")
    @ExcelField(sort = 8, required = false)
    private String unit;

    @ApiModelProperty(value = "频率", name = "remark", example = "频率")
    @ExcelField(sort = 9, required = false)
    private String remark;



    @ApiModelProperty(value = "采集OPC", name = "node_id", example = "采集OPC")
    @ExcelField(sort = 10, required = false)
    private String node_id;


    // CEMS 类别  1539730750575002
    private String facility_type_id;

    //取值类型  1一次  2最小值  3最大值  4平均值  5累计次数
    private String type;

    //取值频率 秒  30  60 300
    private String period;

    //数据类型 1 int   2  float 3  bool   4 string
    private String format;

    //状态 1 0
    private String status;

    //是否为opc采集 1 0
    private String is_opc;
}
