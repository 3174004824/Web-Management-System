package com.example.webproject.service;

import com.example.webproject.domain.SuccessForm;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SuccessFormService {

    Integer deleteFromSuccess(com.example.webproject.domain.SuccessForm sf);

    Integer updateFromSuccess(com.example.webproject.domain.SuccessForm sf);

    PageInfo selectAll(Integer pageNum, Integer pageSize);
    List selectAll();
}
