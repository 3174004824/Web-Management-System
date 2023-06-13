package com.example.webproject.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.example.webproject.aop.OpLog;
import com.example.webproject.domain.SuccessForm;
import com.example.webproject.domain.SysParam;
import com.example.webproject.service.impl.SuccessFormServiceImpl;
import com.example.webproject.service.impl.SysParamServiceImpl;
import com.example.webproject.service.impl.UserServiceImpl;
import com.example.webproject.util.ResultTool;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("data")
public class DataController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    SuccessFormServiceImpl successFormService;

    @Autowired
    SysParamServiceImpl service;

//    @OpLog("更新用户信息操作")
//    @PostMapping("/updateUser")
//    @PreAuthorize("hasAnyAuthority('Admin','updateAuthority')")
//    public ResultTool updateUser(@RequestBody User user){
//        if (user == null){
//            return new ResultTool(400,"参数不能为空");
//        }
//        Integer result = userService.updateUser(user);
//        if (result < 1){
//            return new ResultTool(400,"更新失败");
//        }else {
//            return new ResultTool(200,"更新成功");
//        }
//    }

    @OpLog("移除已到达终点用户操作")
    @PostMapping("/deleteSuccess")
    @PreAuthorize("hasAnyAuthority('Admin','deleteAuthority')")
    public ResultTool deleteSuccess(@RequestBody SuccessForm sf){
        Logger logger = Logger.getLogger(this.getClass());
        if (sf == null){
            logger.info("SuccessForm: " + sf);
            return new ResultTool(400,"参数不能为空");
        }
        try {
            Integer result = successFormService.deleteFromSuccess(sf);
            if (result < 1){
                return new ResultTool(400,"删除失败");
            }else {
                return new ResultTool(200,"删除成功");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("更新已到达终点用户信息操作")
    @PostMapping("/updateSuccess")
    @PreAuthorize("hasAnyAuthority('Admin','updateAuthority')")
    public ResultTool updateSuccess(@RequestBody SuccessForm sf){
        Logger logger = Logger.getLogger(this.getClass());
        if (sf == null){
            logger.info("SuccessForm: " + sf);
            return new ResultTool(400,"参数不能为空");
        }
        try {
            Integer result = successFormService.updateFromSuccess(sf);
            if (result < 1){
                return new ResultTool(400,"更新失败");
            }else {
                return new ResultTool(200,"更新成功");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("查询所有到达终点人员操作")
    @PostMapping("/selectAll")
    public ResultTool selectAll(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            PageInfo pageInfo = successFormService.selectAll(pageNum, pageSize);
            return new ResultTool(200,"查询成功",pageInfo);
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统错误");
        }
    }

    @OpLog("查询所有系统参数信息")
    @PostMapping("/sysparam")
    public ResultTool sysparam(){
        Logger logger = Logger.getLogger(this.getClass());
        try {
            List<SysParam> sysParams = service.selectAllParam();
            return new ResultTool(200,"查询成功",sysParams);
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"查询失败");
        }
    }

    @OpLog("更改系统参数")
    @PostMapping("/updateSysParam")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResultTool updateSysParam(@RequestParam("id")String id,@RequestParam("data")String data){
        Logger logger = Logger.getLogger(this.getClass());
        if (StrUtil.isBlankIfStr(id) || StrUtil.isBlankIfStr(data)){
            logger.info("id: " + id + "    data: " + data);
            return new ResultTool(400,"参数为空");
        }
        try {
            Integer integer = service.updateParam(id, data);
            if (integer < 0){
                return new ResultTool(400,"更新失败");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"更新失败");
        }
        return new ResultTool(200,"更新成功");
    }

    @PostMapping("/selectLock")
    public ResultTool selectLock(@RequestBody Map map){
        Logger logger = Logger.getLogger(this.getClass());
        PageInfo pageInfo = null;
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        try {
            pageInfo = userService.selectLockUser(pageNum,pageSize);
            if (pageInfo == null){
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


    @PostMapping("/unlockUser")
    @PreAuthorize("hasAnyAuthority('Admin','deleteAuthority')")
    public ResultTool unlockUser(@RequestParam("openid") String openid){
        Logger logger = Logger.getLogger(this.getClass());
        if (StrUtil.isBlankIfStr(openid)){
            logger.info("openid: " + openid);
            return new ResultTool(400,"参数不能为空");
        }
        try {
            Boolean result = userService.unlockUser(openid);
            if (!result){
                logger.info("解封失败");
                return new ResultTool(400,"解禁失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常详细信息",e);
            return new ResultTool(400,"系统错误");
        }
        return new ResultTool(200,"解禁成功");
    }
}
