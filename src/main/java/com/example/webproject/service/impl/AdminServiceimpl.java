package com.example.webproject.service.impl;

import com.example.webproject.dao.AdminDao;
import com.example.webproject.dao.PermissionDao;
import com.example.webproject.domain.Admin;
import com.example.webproject.domain.Log;
import com.example.webproject.security.service.UserDetailsServiceImpl;
import com.example.webproject.service.AdminService;
import com.example.webproject.util.JwtUtils;
import com.example.webproject.util.ResultTool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceimpl implements AdminService {

    @Autowired
    JwtUtils jwtTokenUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    LogServiceImpl logService;

    @Override
    public Admin selectByAccount(String account) {
        Admin admin = adminDao.selectByAccount(account);
        if (admin == null){
            return null;
        }
        return admin;
    }

//    @Override
//    public ResultTool login(String username, String password, HttpServletRequest httpServletRequest) {
//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        ResultTool resultTool;
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        if (!passwordEncoder.matches(password,userDetails.getPassword())){
//            resultTool =  new ResultTool(400,"账号密码不匹配");
//            logger.info("账号密码不匹配================" + resultTool);
//            return resultTool;
//        }else {
//            String token = jwtTokenUtils.getToken(username);
//            Log log = new Log();
//            log.setUsername(username);
//            log.setSystem("login()");
//            log.setType("登录操作");
//            log.setResult("成功");
//            log.setIp(getIP(httpServletRequest));
//            log.setOt(new Date().toString());
//            logService.save(log);
//            return new ResultTool(200,"登录成功",token);
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultTool addAccount(String username,String password) {
        ResultTool resultTool;
        Logger logger = LoggerFactory.getLogger(this.getClass());
        if (StringUtils.isEmpty(username)){
            logger.info("username: " + username);
            return new ResultTool(400,"账号为空");
        }
        if (StringUtils.isEmpty(password)){
            logger.info("password: " + password);
            return new ResultTool(400,"密码为空");
        }
        Date nowDate = new Date();
        Date updateDate = new Date();
        Admin admin = new Admin(username,password,nowDate,updateDate);
        Admin admin2 = adminDao.selectByAccount(username);
        if (admin2 != null){
            logger.info("已存在用户? admin:" + admin2);
            resultTool =  new ResultTool(400,"用户已存在");
            return resultTool;
        }
        Integer admin1 = adminDao.addAccount(admin.getUsername(),admin.getPassword(),admin.getLastLoginTime(),admin.getUpdateTime());
        if (admin1 != 0){
            String token = jwtTokenUtils.getToken(username);
            resultTool = new ResultTool(200,"注册成功",token);
            logger.info("注册成功 ==== " + resultTool);
            return resultTool;
        }
        return new ResultTool(400,"注册失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultTool deleteAdmin(String username) {
        if (StringUtils.isEmpty(username)){
            return new ResultTool(400,"删除失败");
        }
        try {
            Integer result1 = adminDao.deleteAdmin(username);
            permissionDao.deleteByUsername(username);
            if (result1 > 0){
                return new ResultTool(200,"删除成功");
            }else {
                return new ResultTool(400,"删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageInfo selectAllAdmin(Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<Admin> admins = adminDao.selectAllAdmin();
            PageInfo pageInfo = new PageInfo(admins);
            return pageInfo;
            //return admins;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
