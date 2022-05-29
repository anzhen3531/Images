package com.anzhen.controller;

import com.anzhen.common.info.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public UserInfo findUserInfo() {
        System.out.println(passwordEncoder.encode("123456"));
        return null;
    }

}
