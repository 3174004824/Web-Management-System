package com.example.webproject.security.handler;

import cn.hutool.json.JSONUtil;
import com.example.webproject.util.ResultTool;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");//执行完JwtAuthenticationFilter过滤器之后执行此方法
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();
        ResultTool resultTool = new ResultTool(400,"无此权限");
        outputStream.write(JSONUtil.toJsonStr(resultTool).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
