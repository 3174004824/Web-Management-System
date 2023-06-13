package com.example.webproject.security.handler;

import com.alibaba.fastjson.JSON;
import com.example.webproject.util.JwtUtils;
import com.example.webproject.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登出成功处理器
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ResultTool result = new ResultTool(200,"退出成功");
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
