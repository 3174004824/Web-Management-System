package com.example.webproject.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.webproject.dao.MapDao;
import com.example.webproject.domain.Map;
import com.example.webproject.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapDao mapDao;

    @Override
    public List<Map> selectAll() {
        List<Map> maps = mapDao.selectAll();
        return maps;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteByStation(String station) {
        return mapDao.deleteByStation(station);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateMap(Map map) {
        if (StringUtils.isEmpty(map.getStation()) || StringUtils.isEmpty(map.getDistance().toString())
                || StringUtils.isEmpty(map.getLongitude()) || StringUtils.isEmpty(map.getLatitude())
                || StringUtils.isEmpty(map.getTitle()) || StringUtils.isEmpty(map.getContent())){
            return 0;
        }
        Integer result = mapDao.updateMap(map);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addMap(Map map) {
        if (StringUtils.isEmpty(map.getStation()) || StringUtils.isEmpty(map.getDistance().toString())
                || StringUtils.isEmpty(map.getLongitude()) || StringUtils.isEmpty(map.getLatitude())
                || StringUtils.isEmpty(map.getTitle()) || StringUtils.isEmpty(map.getContent())){
            return 0;
        }
        Integer result = mapDao.addMap(map);
        return result;
    }
}
