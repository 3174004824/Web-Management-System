package com.example.webproject.dao;

import com.example.webproject.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockUser {

    @Insert("insert into lock_user(openid,stunum,name,nickname,totalstepsnum,todaystepsnum,avatarUrl,timeStamp,partment) values(#{openid},#{stunum},#{name},#{nickname},#{totalstepsnum},#{todaystepsnum},#{avatarUrl},#{timeStamp},#{partment})")
    Integer insertLockUser(User user);

    @Select("select * from lock_user")
    List<User> selectAll();

    @Delete("delete from lock_user where openid = #{openid}")
    Integer unlockUser(String openid);
}
