package com.example.webproject.service;

import com.example.webproject.domain.Log;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface LogService {

    PageInfo queryAllLog(Integer pageNum, Integer pageSize);

    @Async
    void save(Log log);

    PageInfo queryLoginLog(Integer pageNum,Integer pageSize);

    PageInfo queryOpLog(Integer pageNum,Integer pageSize);

}
