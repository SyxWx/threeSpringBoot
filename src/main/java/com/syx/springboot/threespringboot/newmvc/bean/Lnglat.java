package com.syx.springboot.threespringboot.newmvc.bean;


import io.swagger.annotations.ApiModelProperty;
import littlebee.excel.ExcelField;
import lombok.Data;

@Data
public class Lnglat {

    @ApiModelProperty(value = "客户ID", name = "customerid", example = "客户ID")
    @ExcelField(sort = 1, required = false)
    private  String customerid;

    @ApiModelProperty(value = "MN", name = "device_no", example = "MN")
    @ExcelField(sort = 2, required = false)
    private String device_no;

    @ApiModelProperty(value = "经纬度坐标", name = "lnglat", example = "经纬度坐标")
    @ExcelField(sort = 3, required = false)
    private String lnglat;

    @ApiModelProperty(value = "纬度坐标", name = "lat", example = "纬度坐标")
    private String lat;

    @ApiModelProperty(value = "经度坐标", name = "lng", example = "经度坐标")
    private String lng;


}
