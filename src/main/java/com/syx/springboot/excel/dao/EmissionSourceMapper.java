package com.syx.springboot.excel.dao;

import org.mapstruct.Mapper;

import java.util.Map;
import java.util.List;


@Mapper
public interface EmissionSourceMapper {

    //查询List
    List<Map<String,Object>> findEmissions(Map map);
}
