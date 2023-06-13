package com.example.webproject.controller;

import com.example.webproject.util.HomePageManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class ExportController {


    @Autowired
    HomePageManager homePageManager;


    @PreAuthorize("hasAnyAuthority('Admin','updateAuthority')")
    @GetMapping("/exportToExcel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        Workbook wb = homePageManager.exportToExcel();
        OutputStream output = response.getOutputStream();
        String fileName = "接入详情.xlsx";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);
        wb.write(output);
        output.close();
    }
}
