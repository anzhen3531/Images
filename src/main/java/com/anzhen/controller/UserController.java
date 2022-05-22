package com.anzhen.controller;

import com.anzhen.common.ResponseMsg;
import com.anzhen.common.form.UserForm;
import com.anzhen.common.info.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/info")
    public UserInfo findUserInfo() {
        return null;
    }

    @PostMapping("/login")
    public ResponseMsg login(UserForm user) {
        // AuthenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);


        return ResponseMsg.ok("完成");
    }
}
