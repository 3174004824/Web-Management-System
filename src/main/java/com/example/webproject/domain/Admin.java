package com.example.webproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private String username;

    private String password;

    private Date lastLoginTime;

    private Date updateTime;

    private List permission;

    public Admin(String username, String password, Date lastLoginTime, Date updateTime) {
        this.username = username;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.updateTime = updateTime;
    }
}
