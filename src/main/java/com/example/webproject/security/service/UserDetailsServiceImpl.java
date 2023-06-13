package com.example.webproject.security.service;

import com.example.webproject.dao.AdminDao;
import com.example.webproject.dao.SysPermissionDao;
import com.example.webproject.domain.Admin;
import com.example.webproject.domain.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;




@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    SysPermissionDao sysPermissiondao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.length() == 0){
            throw new RuntimeException("用户名不能为空");
        }
        Admin admin = adminDao.selectByAccount(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (admin != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissiondao.selectListByUser(admin.getUsername());
            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermission());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new User(admin.getUsername(), admin.getPassword(), grantedAuthorities);
    }
}
