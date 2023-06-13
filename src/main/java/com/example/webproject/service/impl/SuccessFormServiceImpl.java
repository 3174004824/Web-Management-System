package com.example.webproject.service.impl;

import com.example.webproject.dao.SuccessFormDao;
import com.example.webproject.domain.ExportSuccess;
import com.example.webproject.domain.SuccessForm;
import com.example.webproject.service.SuccessFormService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class SuccessFormServiceImpl implements SuccessFormService {

    @Autowired
    SuccessFormDao successForm;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteFromSuccess(SuccessForm sf) {
        Integer result = successForm.deleteFromSuccess(sf);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateFromSuccess(SuccessForm sf) {
        Integer result = successForm.updateFromSuccess(sf);
        return result;
    }

    @Override
    public PageInfo selectAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SuccessForm> successForms = successForm.selectAll();
        PageInfo pageInfo = new PageInfo(successForms);
        return pageInfo;
        //return successForms;
    }

    @Override
    public List<ExportSuccess> selectAll() {
        List<ExportSuccess> successForms = successForm.selectToExport();
        return successForms;
    }
}
