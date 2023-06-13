package com.example.webproject.dao;

import com.example.webproject.domain.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapDao {

    @Select("select * from map")
    List<Map> selectAll();

    @Delete("delete from map where station = #{station}")
    Integer deleteByStation(String station);

    @Update("update map set distance = #{distance},longitude = #{longitude},latitude = #{latitude},title = #{title},content = #{content} where station = #{station}")
    Integer updateMap(Map map);

    @Insert("insert into map(station,distance,longitude,latitude,title,content) values(#{station},#{distance},#{longitude},#{latitude},#{title},#{content})")
    Integer addMap(Map map);

}
