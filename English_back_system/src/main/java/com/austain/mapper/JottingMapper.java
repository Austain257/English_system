package com.austain.mapper;

import com.austain.domain.po.Jotting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JottingMapper {

    List<Jotting> page();

    @Insert("insert into jottings(english, chinese) values(#{english}, #{chinese})")
    int addJotting(Jotting jotting);

    int updateJotting(Jotting jotting);

    @Delete("delete from jottings where id = #{id}")
    int deleteJotting(String id);

    int batchDeleteJotting(List<String> ids);
}
