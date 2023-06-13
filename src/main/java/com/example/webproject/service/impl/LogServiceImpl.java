package com.example.webproject.service.impl;

import com.example.webproject.dao.LogDao;
import com.example.webproject.domain.Log;
import com.example.webproject.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public PageInfo queryAllLog(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Log> logs = logDao.queryAllLog();
        PageInfo pageInfo = new PageInfo(logs);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Log log) {
        if (log == null){
            return;
        }
        logDao.saveLog(log);
    }

    @Override
    public PageInfo queryLoginLog(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Log> logs = logDao.queryLoginLog();
        PageInfo pageInfo = new PageInfo(logs);
        return pageInfo;
    }

    @Override
    public PageInfo queryOpLog(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Log> logs = logDao.queryOpLog();
        PageInfo pageInfo = new PageInfo(logs);
        return pageInfo;
    }
}
