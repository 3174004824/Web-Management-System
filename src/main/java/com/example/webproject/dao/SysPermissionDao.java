package com.example.webproject.dao;

import com.example.webproject.domain.SysPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionDao {

    @Select("select permission from permission,admin where admin.username = permission.username AND admin.username = #{admin1}")
    List<SysPermission> selectListByUser(String username);

    @Insert("insert into permission(username,permission) values(#{username},#{authority})")
    Integer addAuthority(String username,String authority);

    @Delete("delete from permission where username = #{username} and permission = #{authority}")
    Integer deleteAuthority(String username,String authority);

}
