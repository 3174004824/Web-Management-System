package com.example.webproject.security.handler;

import cn.hutool.core.util.StrUtil;
import com.example.webproject.dao.SysPermissionDao;
import com.example.webproject.domain.Admin;
import com.example.webproject.domain.SysPermission;
import com.example.webproject.security.service.UserDetailsServiceImpl;
import com.example.webproject.service.impl.AdminServiceimpl;
import com.example.webproject.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailService;

    @Autowired
    AdminServiceimpl sysUserService;

    @Autowired
    SysPermissionDao sysPermissiondao;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwt = request.getHeader(jwtUtils.getHeader());//执行完JwtAuthenticationTokenFilter之后执行此方法
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token已过期");
        }
        String username = claim.getSubject();
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        // 获取用户的权限等信息
        Admin admin = sysUserService.selectByAccount(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<SysPermission> sysPermissions = sysPermissiondao.selectListByUser(admin.getUsername());
        // 声明用户授权
        sysPermissions.forEach(sysPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermission());
            grantedAuthorities.add(grantedAuthority);
        });
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}