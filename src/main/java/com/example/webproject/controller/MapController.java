package com.example.webproject.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.example.webproject.aop.OpLog;
import com.example.webproject.domain.Map;
import com.example.webproject.service.impl.MapServiceImpl;
import com.example.webproject.util.ResultTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("map")
public class MapController {

    @Autowired
    MapServiceImpl mapService;


    @OpLog("查询地点")
    @PostMapping("/select")
    public ResultTool selectMap(){
        Logger logger = Logger.getLogger(this.getClass());
        List<Map> maps = null;
        try {
             maps = mapService.selectAll();
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
        return new ResultTool(200,"查询成功",maps);
    }

    @OpLog("删除地点")
    @PreAuthorize("hasAnyAuthority('Admin','deleteAuthority')")
    @PostMapping("/delete")
    public ResultTool deleteByStation(@RequestParam("station") String station){
        Logger logger = Logger.getLogger(this.getClass());
        if (StrUtil.isBlankIfStr(station)){
            logger.info("station: " + station);
            return new ResultTool(400,"参数为空");
        }
        try {
            Integer integer = mapService.deleteByStation(station);
            if (integer > 0){
                return new ResultTool(200,"删除成功");
            }else {
                return new ResultTool(400,"删除失败");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("更新地点")
    @PreAuthorize("hasAnyAuthority('Admin','updateAuthority')")
    @PostMapping("/update")
    public ResultTool updateMap(@RequestBody Map map){
        Logger logger = Logger.getLogger(this.getClass());
        if (map == null){
            logger.info("map: " + map);
            return new ResultTool(400,"参数为空");
        }
        Integer result;
        try {
            result = mapService.updateMap(map);
            if (result > 0){
                return new ResultTool(200,"更新成功");
            }else {
                return new ResultTool(400,"更新失败");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("添加地点")
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('Admin','addAuthority')")
    public ResultTool addMap(@RequestBody Map map){
        Logger logger = Logger.getLogger(this.getClass());
        if (map == null){
            logger.info("map: " + map);
            return new ResultTool(400,"参数为空");
        }
        try {
            Integer result = mapService.addMap(map);
            if (result > 0){
                return new ResultTool(200,"添加成功");
            }else {
                return new ResultTool(400,"添加失败");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }
}
