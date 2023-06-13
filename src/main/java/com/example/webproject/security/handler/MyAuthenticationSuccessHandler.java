package com.example.webproject.security.handler;

import com.alibaba.fastjson.JSON;
import com.example.webproject.aop.OpLog;
import com.example.webproject.dao.AdminDao;
import com.example.webproject.domain.Admin;
import com.example.webproject.util.JwtUtils;
import com.example.webproject.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.Date;


/**
 * 登录成功处理器
 */

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    AdminDao adminDao;

    @Autowired
    JwtUtils jwtTokenUtils;

    @OpLog("登录操作")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtTokenUtils.getToken(userDetails.getUsername());
        Admin sysUser = adminDao.selectByAccount(userDetails.getUsername());
        sysUser.setLastLoginTime(new Date());
        adminDao.update(sysUser);
        ResultTool resultTool = new ResultTool(200,"登录成功",token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(resultTool));

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
    }
}
