package com.example.webproject.controller;


import com.example.webproject.aop.OpLog;
import com.example.webproject.domain.Admin;
import com.example.webproject.security.service.UserDetailsServiceImpl;
import com.example.webproject.service.impl.AdminServiceimpl;
import com.example.webproject.service.impl.PermissionServiceimpl;
import com.example.webproject.util.ResultTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminServiceimpl adminServiceimpl;

    @Autowired
    PermissionServiceimpl permissionServiceimpl;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @OpLog("删除管理员操作")
    @PostMapping("/deleteAdmin")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResultTool deleteAdmin(@RequestBody Admin admin){
        Logger logger = Logger.getLogger(this.getClass());
        if (admin == null){
            logger.info("系统参数为空");
            return new ResultTool(400,"参数为空");
        }else {
            return adminServiceimpl.deleteAdmin(admin.getUsername());
        }
    }

    @OpLog("给管理员授予权限操作")
    @PostMapping("/addAuthority")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResultTool addAuthority(@RequestParam("authority") String authority,@RequestParam("username") String username){
        Logger logger = Logger.getLogger(this.getClass());
        if (authority == null || authority.length() == 0){
            logger.info("authority: "+ authority);
            return new ResultTool(400,"参数不能为空");
        }

        if (username == null || username.length() == 0){
            logger.info("username: " + username);
            return new ResultTool(400,"参数不能为空");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (authority.equals("addAuthority") || authority.equals("deleteAuthority") || authority.equals("updateAuthority")){

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            if (authorities.contains(grantedAuthority)){
                return new ResultTool(400,"用户已拥有该权限");
            }
            Integer result = 0;
            try {
                 result = permissionServiceimpl.addAuthority(userDetails.getUsername(), authority);
            }catch (Exception e){
                logger.error("异常详细信息",e);
                e.printStackTrace();
                return new ResultTool<>(400,"授权失败");
            }
            if (result > 0){
                return new ResultTool(200,"授权成功");
            }
        }else {
            return new ResultTool(400,"权限参数错误");
        }
        return new ResultTool(400,"授权失败");
    }

    @OpLog("给管理员删除权限操作")
    @PostMapping("/deleteAuthority")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResultTool deleteAuthority(@RequestParam("authority") String authority,@RequestParam("username") String username){
        Logger logger = Logger.getLogger(this.getClass());
        if (authority == null || authority.length() == 0){
            logger.info("authority: " + authority);
            return new ResultTool(400,"参数不能为空");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (authority.equals("addAuthority") || authority.equals("deleteAuthority") || authority.equals("updateAuthority")){
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            if (authorities.contains(grantedAuthority)){
                try {
                    Integer result = permissionServiceimpl.deleteAuthority(userDetails.getUsername(), authority);
                    if (result > 0){
                        return new ResultTool(200,"删除成功");
                    }else {
                        return new ResultTool(400,"删除失败");
                    }
                }catch (Exception e){
                    logger.error("异常详细信息",e);
                    e.printStackTrace();
                    return new ResultTool(400,"系统错误");
                }
            }else {
                return new ResultTool(400,"该管理员无此权限");
            }
        }else {
            return new ResultTool(400,"权限参数错误");
        }
    }
}
