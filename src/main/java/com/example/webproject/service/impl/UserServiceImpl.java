package com.example.webproject.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.example.webproject.dao.LockUser;
import com.example.webproject.dao.SuccessFormDao;
import com.example.webproject.dao.UserDao;
import com.example.webproject.domain.SuccessForm;
import com.example.webproject.domain.User;
import com.example.webproject.service.UserService;
import com.example.webproject.util.StartTimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    SuccessFormDao successFormDao;

    @Autowired
    LockUser lockUser;

    @Override
    public PageInfo selectAll(Integer pageNum,Integer pageSize,String partment) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<User> users;
            String nowTime = StartTimeUtil.getTodayStartTime()/1000 + "";
            if (StrUtil.isBlankIfStr(partment)){
                users = userDao.selectAll(nowTime);
            }else {
                users = userDao.selectByPartment(partment,nowTime);
            }
            PageInfo pageInfo = new PageInfo(users);
            return pageInfo;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    public PageInfo selectByStu(Integer pageNum,Integer pageSize,String stunum){
        try {
            String nowTime = StartTimeUtil.getTodayStartTime()/1000 + "";
            PageHelper.startPage(pageNum,pageSize);
            List<User> users;
            if (StrUtil.isBlankIfStr(stunum)){
                users = userDao.selectAll(nowTime);
            }else {
                users = userDao.selectByStu(stunum);
            }
            PageInfo pageInfo = new PageInfo(users);
            return pageInfo;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }




    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUser(String username){
        Integer result = userDao.deleteByOpenid(username);
        SuccessForm successForm1 = successFormDao.selectByOpenid(username);
        if (successForm1 == null){
            return result;
        }
        SuccessForm successForm = new SuccessForm();
        successForm.setOpenid(username);
        Integer result1 = successFormDao.deleteFromSuccess(successForm);
        return result == result1 ? result : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer lockUser(User user){
        String openid = user.getOpenid();
        User temp_user = userDao.selectByOpenid(openid);
        Integer result1 = lockUser.insertLockUser(temp_user);
        Integer result2 = userDao.deleteByOpenid(openid);
        if (result1 > 0 && result2 > 0){
            return 1;
        }else {
            return 0;
        }
    }

    public PageInfo selectLockUser(Integer pageNum,Integer pageSize){
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<User> users;
            users = lockUser.selectAll();
            PageInfo pageInfo = new PageInfo(users);
            return pageInfo;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean unlockUser(String openid){
        Integer result = lockUser.unlockUser(openid);
        if (result > 0){
            return true;
        }
        return false;
    }


}
