package com.example.webproject.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class ResultTool<T> implements Serializable {

    Integer code;



    String msg;

    T data;

    public ResultTool(Integer code) {
        this.code = code;
    }

    public ResultTool(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultTool(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
