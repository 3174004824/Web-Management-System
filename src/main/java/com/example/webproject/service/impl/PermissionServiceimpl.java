package com.example.webproject.service.impl;

import com.example.webproject.dao.AdminDao;
import com.example.webproject.dao.SysPermissionDao;
import com.example.webproject.domain.Admin;
import com.example.webproject.service.SysPermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PermissionServiceimpl implements SysPermissionService {

    @Autowired
    SysPermissionDao sysPermissionDao;

    @Autowired
    AdminDao adminDao;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addAuthority(String username, String authority) {
        if (username == null || authority == null){
            return 0;
        }
        Integer result = sysPermissionDao.addAuthority(username, authority);
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setUpdateTime(new Date());
        adminDao.updateUpdateTime(admin);
        if (result > 0){
            return 1;
        }
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteAuthority(String username, String authority) {
        Logger logger = Logger.getLogger(this.getClass());
        if (username == null || authority == null){
            logger.info("username: " + username + "   authority: " + authority);
            return 0;
        }
        Integer result = sysPermissionDao.deleteAuthority(username, authority);
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setUpdateTime(new Date());
        adminDao.updateUpdateTime(admin);
        if (result > 0){
            return 1;
        }
        return 0;
    }
}
