package com.example.webproject.dao;

import com.example.webproject.domain.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdminDao {

    @Select("select * from admin where username = #{username}")
    Admin selectByAccount(String username);

    @Update("update admin set lastLoginTime = #{lastLoginTime} where username = #{username}")
    void update(Admin admin);

    @Update("update admin set UpdateTime = #{updateTime} where username = #{username}")
    void updateUpdateTime(Admin admin);

    @Insert("insert into admin(username,password,lastLoginTime,updateTime) values(#{username},#{password},#{lastLoginTime},#{updateTime})")
    Integer addAccount(String username, String password, Date lastLoginTime,Date updateTime);

    @Delete("delete from admin where username = #{username}")
    Integer deleteAdmin(String username);

    List<Admin> selectAllAdmin();


}
