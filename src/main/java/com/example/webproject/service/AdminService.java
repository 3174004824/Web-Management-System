package com.example.webproject.service;


import com.example.webproject.domain.Admin;
import com.example.webproject.util.ResultTool;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {

    Admin selectByAccount(String account);

    //public ResultTool login(String username, String password, HttpServletRequest httpServletRequest);

    ResultTool addAccount(String username,String password);

    ResultTool deleteAdmin(String username);

    PageInfo selectAllAdmin(Integer pageNum, Integer pageSize);
}
