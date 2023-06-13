package com.example.webproject.service;

import com.example.webproject.domain.Map;

import java.util.List;

public interface MapService {

    List<Map> selectAll();

    Integer deleteByStation(String station);

    Integer updateMap(Map map);

    Integer addMap(Map map);

}
