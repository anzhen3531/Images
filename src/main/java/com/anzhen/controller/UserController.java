package com.anzhen.controller;

import com.anzhen.common.info.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/user")
public class UserController {

    @GetMapping("/info")
    public UserInfo findUserInfo() {
        return null;
    }

}
