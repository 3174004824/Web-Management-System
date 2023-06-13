package com.example.webproject.filter;

import com.example.webproject.security.service.UserDetailsServiceImpl;
import com.example.webproject.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtTokenUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(jwtTokenUtils.getHeader());//拿到请求头中的Token
        //判断当前请求中是否包含令牌
        if (!StringUtils.isEmpty(token)) {//(1)
            //从token中获取用户信息
            Claims claims = jwtTokenUtils.getTokenClaim(token);
            if (claims != null) {//如果用户信息不为空进一步确认Token是否过期
                //如果token未失效 并且当前上下文权限凭证为null
                if (!jwtTokenUtils.isTokenExpired(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 从token中提取出之前存储好的用户名
                    String username = claims.getSubject();
                    // 查询出用户对象
                    UserDetails user = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken
                                    (user, null, user.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);//执行下一个过滤器
    }//(3)
}
