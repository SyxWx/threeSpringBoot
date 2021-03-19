package com.syx.springboot.excel.bean;

import io.swagger.annotations.ApiModelProperty;
import littlebee.excel.ExcelField;
import lombok.Data;

@Data
public class EmissionSource {
    @ApiModelProperty(value = "客户ID", name = "customerid", example = "客户ID")
    @ExcelField(sort = 1, required = false)
    private  String customerid;

    @ApiModelProperty(value = "清单ID", name = "emissionsourceno", example = "清单ID")
    @ExcelField(sort = 2, required = false)
    private  String emissionsourceno;


    @ApiModelProperty(value = "排放源清单类型", name = "emissionsourcetype", example = "排放源清单类型")
    @ExcelField(sort = 3, required = false)
    private  String emissionsourcetype;


    @ApiModelProperty(value = "分厂/所属区域", name = "branchfactory", example = "分厂/所属区域")
    @ExcelField(sort = 4, required = false)
    private  String branchfactory;



    @ApiModelProperty(value = "生产工艺线", name = "productionline", example = "生产工艺线")
    @ExcelField(sort = 5, required = false)
    private  String productionline;

    @ApiModelProperty(value = "工序", name = "processes", example = "工序")
    @ExcelField(sort = 6, required = false)
    private  String processes;


    @ApiModelProperty(value = "工艺区间", name = "processsection", example = "工艺区间")
    @ExcelField(sort = 7, required = false)
    private  String processsection;

    @ApiModelProperty(value = "排放源名称", name = "emissionsourcename", example = "排放源名称")
    @ExcelField(sort = 8, required = false)
    private  String emissionsourcename;

    @ApiModelProperty(value = "输送物料", name = "materiel", example = "输送物料")
    @ExcelField(sort = 9, required = false)
    private  String materiel;

    @ApiModelProperty(value = "静态生产设备", name = "staticproductionname", example = "静态生产设备")
    @ExcelField(sort = 10, required = false)
    private  String staticproductionname;

    @ApiModelProperty(value = "静态生产设备基本信息", name = "staticproductioninfo", example = "静态生产设备基本信息")
    @ExcelField(sort = 11, required = false)
    private  String staticproductioninfo;

    @ApiModelProperty(value = "封闭情况（企业实际情况+问题）", name = "isclose", example = "封闭情况（企业实际情况+问题）")
    @ExcelField(sort = 12, required = false)
    private  String isclose;

    @ApiModelProperty(value = "照片名称", name = "imageurl", example = "照片名称")
    @ExcelField(sort = 13, required = false)
    private  String imageurl;

    @ApiModelProperty(value = "有组织/无组织", name = "organizationtype", example = "有组织/无组织")
    @ExcelField(sort = 14, required = false)
    private  String organizationtype;

    @ApiModelProperty(value = "备注", name = "remark", example = "备注")
    @ExcelField(sort = 15, required = false)
    private  String remark;
    

}
