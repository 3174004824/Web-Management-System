package com.example.webproject.service;

import com.example.webproject.domain.SysParam;

import java.util.List;

public interface SysParamService {

    List<SysParam> selectAllParam();

    Integer updateParam(String id,String data);
}
