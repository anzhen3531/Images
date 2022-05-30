package com.anzhen.controller;

import com.anzhen.common.form.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountLoginController {

    @Resource
    RestTemplate restTemplate;
    @Resource
    TokenEndpoint tokenEndpoint;


    /**
     * github 第三方接口登录
     *
     * @param code
     * @return 返回token
     */
    @GetMapping("/auto/login")
    public void autoLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        // todo 账号密码登录已经可以通过 springSecurityOauth2 方式自带解决
        // 先获取用户id
        // 判断是否存在 如果不存在则进行创建  存在则进行调用 自动登录

    }
}
