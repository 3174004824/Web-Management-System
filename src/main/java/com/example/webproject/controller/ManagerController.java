package com.example.webproject.controller;


import com.example.webproject.aop.OpLog;
import com.example.webproject.domain.Admin;
import com.example.webproject.domain.Log;
import com.example.webproject.service.impl.AdminServiceimpl;
import com.example.webproject.service.impl.LogServiceImpl;
import com.example.webproject.util.ResultTool;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("manager")
public class ManagerController {

    @Autowired
    AdminServiceimpl adminServiceimpl;

    @Autowired
    LogServiceImpl logService;

    @OpLog("查询所有管理员操作")
    @PostMapping("/all")
    public ResultTool selectAllAdmin(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            PageInfo pageInfo = adminServiceimpl.selectAllAdmin(pageNum, pageSize);
            if (pageInfo == null){
                logger.info("pageInfo:  " + pageInfo);
                return new ResultTool(400,"查询失败");
            }
            return new ResultTool(200,"查询成功",pageInfo);
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("查询日志操作")
    @PostMapping("/querylog")
    public ResultTool selectLog(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            PageInfo pageInfo = logService.queryAllLog(pageNum, pageSize);
            if (pageInfo == null){
                logger.info("pageInfo: " + pageInfo);
                return new ResultTool(400,"查询失败");
            }else {
                return new ResultTool(200,"查询成功",pageInfo);
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("查询登录日志操作")
    @PostMapping("/loginLog")
    public ResultTool selectLoginLog(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            PageInfo pageInfo = logService.queryLoginLog(pageNum, pageSize);
            if (pageInfo == null){
                logger.info("pageInfo: " +pageInfo);
                return new ResultTool(400,"查询失败");
            }else {
                return new ResultTool(200,"查询成功",pageInfo);
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("查询操作日志操作")
    @PostMapping("/operateLog")
    public ResultTool selectOperateLog(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            PageInfo pageInfo = logService.queryOpLog(pageNum, pageSize);
            if (pageInfo == null){
                logger.info("pageInfo: " + pageInfo);
                return new ResultTool(400,"查询失败");
            }else {
                return new ResultTool(200,"查询成功",pageInfo);
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }
}
