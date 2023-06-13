package com.example.webproject.util;

import com.example.webproject.service.impl.SuccessFormServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomePageManager {

    @Autowired
    SuccessFormServiceImpl successFormService;



    public Workbook exportToExcel() {
        //这是表头
        String[] arr = {"学号","姓名","昵称","完成时间","学院"};
        //这是具体数据
        List lists = successFormService.selectAll();
        return ExcelWrite.writeToExcelByList(arr, lists);
    }
}
