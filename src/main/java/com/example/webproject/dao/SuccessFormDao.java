package com.example.webproject.dao;

import com.example.webproject.domain.ExportSuccess;
import com.example.webproject.domain.SuccessForm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuccessFormDao {

    @Select("select * from successInfo where openid = #{openid}")
    SuccessForm selectByOpenid(String openid);

    @Delete("delete from successInfo where openid = #{openid}")
    Integer deleteFromSuccess(SuccessForm sf);

    @Update("update successInfo set successtime = #{successtime} where openid = #{openid}")
    Integer updateFromSuccess(SuccessForm sf);

    //@Select("select * from successInfo")
    List<SuccessForm> selectAll();


    @Select("select stunum,name,nickname,successtime,partment from successInfo join user on successInfo.openid = user.openid")
    List<ExportSuccess> selectToExport();

}
