package com.example.webproject.dao;

import com.example.webproject.domain.SysParam;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysParamDao {

    @Select("select * from sysparam")
    List<SysParam> selectAllParam();

    @Update("update sysparam set data = #{data} where id = #{id}")
    Integer updateParam(String id,String data);
}
