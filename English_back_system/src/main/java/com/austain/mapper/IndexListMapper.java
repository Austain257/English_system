package com.austain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndexListMapper {

    @Select("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'english'  AND TABLE_TYPE = 'BASE TABLE'")
    List<String> getBookNameList();
}
