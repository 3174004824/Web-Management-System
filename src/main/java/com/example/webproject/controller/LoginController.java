package com.example.webproject.controller;


import com.example.webproject.aop.OpLog;
import com.example.webproject.service.impl.AdminServiceimpl;
import com.example.webproject.util.ResultTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    AdminServiceimpl adminServiceimpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    //@OpLog("登录操作")
//    @PostMapping("/login")
//    public ResultTool login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest httpServletRequest){
//        return adminServiceimpl.login(username, password,httpServletRequest);
//    }

    @OpLog("添加管理员操作")
    @PostMapping("/createAdmin")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResultTool register(@RequestParam("username") String username,@RequestParam("password") String password){
        Logger logger = Logger.getLogger(this.getClass());
        ResultTool resultTool = null;
        try {
            resultTool = adminServiceimpl.addAccount(username, passwordEncoder.encode(password));
        }catch (Exception e){
            logger.error("异常详细信息",e);
            e.printStackTrace();
            return new ResultTool(400,"系统异常");
        }
        return resultTool;
    }
}
