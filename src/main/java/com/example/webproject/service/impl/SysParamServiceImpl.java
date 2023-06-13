package com.example.webproject.service.impl;

import com.example.webproject.dao.SysParamDao;
import com.example.webproject.domain.SysParam;
import com.example.webproject.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class SysParamServiceImpl implements SysParamService {

    @Autowired
    SysParamDao sysParamDao;

    @Override
    public List<SysParam> selectAllParam() {
        List<SysParam> sysParams = sysParamDao.selectAllParam();
        return sysParams;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateParam(String id, String data) {
        Integer integer = sysParamDao.updateParam(id, data);
        return integer;
    }
}
