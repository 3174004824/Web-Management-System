package com.example.webproject.security.handler;

import com.alibaba.fastjson.JSON;
import com.example.webproject.util.ResultTool;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录失败处理器
 */

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        ResultTool result = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = new ResultTool(400,"账号过期");
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = new ResultTool(400,"密码错误");
        }else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = new ResultTool(400,"用户不存在");
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
