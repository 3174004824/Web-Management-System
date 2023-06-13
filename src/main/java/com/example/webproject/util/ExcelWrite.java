package com.example.webproject.util;

import com.example.webproject.domain.ExportSuccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelWrite {
    public static Workbook writeToExcelByList(String[] array, List<ExportSuccess> list) {
        //创建工作薄
        Workbook wb = new XSSFWorkbook();
        //标题和页码
        CellStyle titleStyle = wb.createCellStyle();
        // 设置单元格对齐方式,水平居左
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        // 设置字体样式
        Font titleFont = wb.createFont();
        // 字体高度
        titleFont.setFontHeightInPoints((short) 12);
        // 字体样式
        titleFont.setFontName("黑体");
        titleStyle.setFont(titleFont);
        //创建sheet
        Sheet sheet = wb.createSheet("到达用户详情");
        // 自动设置宽度
        sheet.autoSizeColumn(0);
        // 在sheet中添加标题行// 行数从0开始
        Row row = sheet.createRow((int) 0);
        for (int i = 0; i < array.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(array[i]);
            cell.setCellStyle(titleStyle);
        }
        // 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖
        CellStyle dataStyle = wb.createCellStyle();
        // 设置居中样式，水平居中
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        //数据从序号1开始
        try {
            int index = 1;
            for (ExportSuccess value : list) {
                // 默认的行数从0开始，为了统一格式设置从1开始，就是从excel的第二行开始
                row = sheet.createRow(index);
                index++;
                ExportSuccess data = value;
//                for (int j = 0; j < data.size(); j++) {
//                    Cell cell = row.createCell(j);
//                    // 为当前列赋值
//                    cell.setCellValue(data.get(j).toString());
//                    //设置数据的样式
//                    cell.setCellStyle(dataStyle);
//                }
                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(data.getStunum());
                cell_0.setCellStyle(dataStyle);

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(data.getName());
                cell_1.setCellStyle(dataStyle);

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(data.getNickname());
                cell_2.setCellStyle(dataStyle);

                Cell cell_3 = row.createCell(3);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long date = Long.valueOf(data.getSuccesstime());
                String date_time = formatter.format(new Date(date*1000L));
                cell_3.setCellValue(date_time);
                cell_3.setCellStyle(dataStyle);

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(data.getPartment());
                cell_4.setCellStyle(dataStyle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }
}
