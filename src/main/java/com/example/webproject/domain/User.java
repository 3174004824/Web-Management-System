package com.example.webproject.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String openid;

    private String stunum;

    private String name;

    private String nickname;

    private Integer totalstepsnum;

    private Integer todaystepsnum;

    private String avatarUrl;

    private String timeStamp;

    private String partment;
}
