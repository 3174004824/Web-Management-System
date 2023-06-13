package com.example.webproject.security.handler;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {

}
