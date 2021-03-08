package com.syx.springboot.threespringboot.newmvc.importimpl;

import com.syx.springboot.threespringboot.newmvc.bean.EmissionSourceBean;
import littlebee.excel.ExcelImport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/*
*
 * @Author Song YuXin
 * @Description //TODO 
 * @Date 13:59 2021/3/4
 **/
public class ImportEmissionSource {



    /*
    *
     * @Author Song YuXin
     * @Date 14:29 2021/3/4
     * @Description //TODO
     * @Param []
     * @return void
     **/
    @Test
    public void testToEmissionSourceInserSQl() throws  Exception {

        List<EmissionSourceBean> list = new ArrayList<>();

        ExcelImport emissionExcel = new ExcelImport(EmissionSourceBean.class, 1, "D:\\BMENEW\\EmissionSource模板.xlsx");

        list = emissionExcel.getModelList(EmissionSourceBean.class);

        /**
         * StringBuffer  线程安全   慢
         * StringBuilder 非线程安全 快
         */
        StringBuilder sql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        String organizationtype = "";
        for (EmissionSourceBean EBdata : list) {
            values.append("(");

            // "客户ID", name = "customerid", example = "客户ID")
            values.append(EBdata.getCustomerid() != null && !EBdata.getCustomerid().isEmpty() ? "'"+EBdata.getCustomerid().trim()+"',":"NULL,");

            // "清单ID", name = "emissionsourceno", example = "清单ID")
            values.append(EBdata.getEmissionsourceno() != null && !EBdata.getEmissionsourceno().isEmpty() ? "'"+EBdata.getEmissionsourceno().trim()+"',":"NULL,");

            // "排放源清单类型", name = "emissionsourcetype", example = "排放源清单类型")
            values.append(EBdata.getEmissionsourcetype() != null && !EBdata.getEmissionsourcetype().isEmpty() ? "'"+EBdata.getEmissionsourcetype().trim()+"',":"NULL,");

            // "分厂/所属区域", name = "branchfactory", example = "分厂/所属区域")
            values.append(EBdata.getBranchfactory() != null && !EBdata.getBranchfactory().isEmpty() ? "'"+EBdata.getBranchfactory().trim()+"',":"NULL,");

            // "生产工艺线", name = "productionline", example = "生产工艺线")
            values.append(EBdata.getProductionline() != null && !EBdata.getProductionline().isEmpty() ? "'"+EBdata.getProductionline().trim()+"',":"NULL,");

            // "工序", name = "processes", example = "工序")
            values.append(EBdata.getProcesses() != null && !EBdata.getProcesses().isEmpty() ? "'"+EBdata.getProcesses().trim()+"',":"NULL,");

            // "工艺区间", name = "processsection", example = "工艺区间")
            values.append(EBdata.getProcesssection() != null && !EBdata.getProcesssection().isEmpty() ? "'"+EBdata.getProcesssection().trim()+"',":"NULL,");

            // "排放源名称", name = "emissionsourcename", example = "排放源名称")
            values.append(EBdata.getEmissionsourcename() != null && !EBdata.getEmissionsourcename().isEmpty() ? "'"+EBdata.getEmissionsourcename().trim()+"',":"NULL,");

            // "输送物料", name = "materiel", example = "输送物料")
            values.append(EBdata.getMateriel() != null && !EBdata.getMateriel().isEmpty() ? "'"+EBdata.getMateriel().trim()+"',":"NULL,");

            // "静态生产设备", name = "staticproductionname", example = "静态生产设备")
            values.append(EBdata.getStaticproductionname() != null && !EBdata.getStaticproductionname().isEmpty() ? "'"+EBdata.getStaticproductionname().trim()+"',":"NULL,");

            // "静态生产设备基本信息", name = "staticproductioninfo", example = "静态生产设备基本信息")
            values.append(EBdata.getStaticproductioninfo() != null && !EBdata.getStaticproductioninfo().isEmpty() ? "'"+EBdata.getStaticproductioninfo().trim()+"',":"NULL,");

            // "封闭情况（企业实际情况+问题）", name = "isclose", example = "封闭情况（企业实际情况+问题）")
            values.append(EBdata.getIsclose() != null && !EBdata.getIsclose().isEmpty() ? "'"+EBdata.getIsclose().trim()+"',":"NULL,");

            // "照片名称", name = "imageurl", example = "照片名称")
            values.append(EBdata.getImageurl() != null && !EBdata.getImageurl().isEmpty() ? "'"+EBdata.getImageurl().trim()+"',":"NULL,");

            // "有组织/无组织", name = "organizationtype", example = "有组织/无组织")
            organizationtype = getOrganizationtypValue(EBdata.getOrganizationtype());
            values.append( organizationtype!=null&&!organizationtype.isEmpty()  ? "'"+organizationtype +"',":"NULL,");

            // "备注", name = "remark", example = "备注")
            values.append(EBdata.getRemark() != null && !EBdata.getRemark().isEmpty() ? "'"+EBdata.getRemark().trim()+"',":"NULL,");

            //是否启用，1：启用，0，未启用，默认1
            values.append("1");


            values.append("),");
        }
        sql.append("insert t_emission_source ( "
                + " customer_id,emission_source_no,emission_source_type, branch_factory,  production_line, "
                + " processes,  process_section,   emission_source_name, materiel,        static_production_name, "
                + " static_production_info,close_detail,image_url,organization_type,remark,is_enable ) "
                + " values "
        );
        sql.append(values);

        System.out.println("-------------新增条数：" + list.size() + " 条 -------------");
        System.out.println(sql);
        System.out.println("----------------------------------------------------------");
    }



    //组织类型，1：无组织，2：有组织
    public String getOrganizationtypValue(String remark){
        if(remark!=null && !remark.isEmpty() && remark.indexOf("有组织")>-1){
                return "2";
        }else{
            return "1";
        }
    }
}
