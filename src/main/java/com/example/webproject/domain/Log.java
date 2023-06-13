package com.example.webproject.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Log {

    //日志编号
    private String id;

    //系统模块
    private String system;

    //操作类型
    private String type;

    //操作人员
    private String username;

    //请求地址
    private String ip;

    //操作时间
    private Date ot;

    //操作结果
    private String result;

}
