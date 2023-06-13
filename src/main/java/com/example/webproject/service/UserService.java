package com.example.webproject.service;

import com.example.webproject.domain.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    PageInfo selectAll(Integer pageNum,Integer pageSize,String partment);


//    Integer updateUser(User user);

}
