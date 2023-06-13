package com.example.webproject.dao;

import com.example.webproject.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from user where timeStamp = #{timeStamp} order by todaystepsnum DESC")
    List<User> selectAll(String timeStamp);

    @Select("select * from user where stunum = #{stunum}")
    List<User> selectByStu(String stunum);

    @Select("select * from user where partment = #{partment} and timeStamp = #{timeStamp} order by todaystepsnum DESC")
    List<User> selectByPartment(String partment,String timeStamp);

    @Delete("delete from user where openid = #{openid}")
    Integer deleteByOpenid(String openid);

//    @Update("update user set totalstepsnum = #{totalstepsnum},todaystepsnum = #{todaystepsnum},classes = #{classes},major = #{major} where stunum = #{stunum}")
//    Integer updateUser(User user);

    @Select("select * from user where openid = #{openid}")
    User selectByOpenid(String openid);
}
