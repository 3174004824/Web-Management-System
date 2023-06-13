package com.example.webproject.dao;

import com.example.webproject.domain.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDao {

    @Insert("insert into log(`system`,type,username,ip,ot,result) values(#{system},#{type},#{username},#{ip},#{ot},#{result})")
    Integer saveLog(Log log);

    @Select("select * from log order by id desc")
    List<Log> queryAllLog();

    @Select("select * from log where type = '登录操作' order by id desc")
    List<Log> queryLoginLog();

    @Select("select * from log where type != '登录操作' order by id desc")
    List<Log> queryOpLog();

}
