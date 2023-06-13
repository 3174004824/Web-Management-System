package com.example.webproject.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.example.webproject.aop.OpLog;
import com.example.webproject.domain.User;
import com.example.webproject.service.impl.UserServiceImpl;
import com.example.webproject.util.ResultTool;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserServiceImpl userService;



//    @OpLog("更新用户密码操作")
//    @PostMapping("/updatePassword")
//    @PreAuthorize("hasAnyAuthority('Admin','updateAuthority')")
//    public ResultTool updatePassword(@RequestParam("username")String username,@RequestParam("password")String password){
//        if (username == null || username.length() == 0){
//            return new ResultTool(400,"账号参数为空");
//        }
//        if (password == null || password.length() == 0){
//            return new ResultTool(400,"密码不能为空");
//        }
//        try {
//            ResultTool resultTool = accountService.updatePassword(username, password);
//            return resultTool;
//        }catch (RuntimeException e){
//            e.printStackTrace();
//        }
//        return new ResultTool(400,"系统错误");
//    }

    @OpLog("删除用户操作")
    @PostMapping("/deleteUser")
    @PreAuthorize("hasAnyAuthority('Admin','deleteAuthority')")
    public ResultTool deleteUser(@RequestParam("username")String username){
        try {
            if (username == null || username.length() == 0){
                return new ResultTool(400,"账号参数不能为空");
            }
            //Integer result = accountService.deleteAccountAndUser(username);
            Integer result = userService.deleteUser(username);

            if (result == 1){
                return new ResultTool(200,"删除用户成功");
            }
            return new ResultTool(400,"删除用户失败");
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return new ResultTool(400,"系统错误");
    }

//    @OpLog("添加用户操作")
//    @PostMapping("/addUser")
//    @PreAuthorize("hasAnyAuthority('Admin','addAuthority')")
//    public ResultTool addUser(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("name")String name){
//        try {
//            Integer integer = userService.addUser(username, password, name);
//            if (integer > 0){
//                return new ResultTool(200,"添加成功");
//            }else {
//                return new ResultTool(400,"添加失败");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResultTool(400,"系统错误");
//        }
//    }

    @OpLog("查询用户操作")
    @PostMapping("/findAll")
    public ResultTool findAllUser(@RequestBody Map map){
        Logger logger = Logger.getLogger(this.getClass());
        PageInfo pageInfo = null;
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        String partment = (String) map.get("partment");
        try {
            pageInfo = userService.selectAll(pageNum, pageSize,partment);
            if (pageInfo == null){
                return new ResultTool(400,"查询失败");
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统异常");
        }
        return new ResultTool(200,"查询成功",pageInfo);
    }

    @OpLog("根据学号查询用户操作")
    @PostMapping("/selectByStu")
    public ResultTool selectByStu(@RequestBody Map map){
        Logger logger = Logger.getLogger(this.getClass());
        PageInfo pageInfo = null;
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        String stunum = (String) map.get("stunum");
        try {
            pageInfo = userService.selectByStu(pageNum,pageSize,stunum);
            if (pageInfo == null){
                return new ResultTool(400,"查询失败");
            }else {
                return new ResultTool(200,"查询成功",pageInfo);
            }
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统异常");
        }
    }

    @OpLog("封禁用户操作")
    @PostMapping("/lock")
    @PreAuthorize("hasAnyAuthority('Admin','deleteAuthority')")
    public ResultTool lockUser(@RequestBody User user){
        Logger logger = Logger.getLogger(this.getClass());
        if (StrUtil.isBlankIfStr(user.getOpenid())){
            return new ResultTool(400,"openid不能为空");
        }
        Integer result;
        try {
             result = userService.lockUser(user);
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统异常");
        }
        if (result == 1){
            return new ResultTool(200,"封禁成功");
        }else {
            return new ResultTool(400,"封禁失败");
        }
    }

}
