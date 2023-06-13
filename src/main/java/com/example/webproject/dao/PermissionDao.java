package com.example.webproject.dao;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao {

    @Delete("delete from permission where username = #{username}")
    Integer deleteByUsername(String username);


}
