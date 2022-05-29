package com.anzhen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountLoginController {

    @Resource
    RestTemplate restTemplate;

    /**
     * github 通过code 获取github信息
     *
     * @param code
     * @return 返回token
     */
    @GetMapping("/auto/login")
    public void autoLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        Map<String, String> map = new HashMap<>();
        // 统一封装成为 github 自动登录
        map.put("client_id", "1c5c71a9fc77bea14869");
        map.put("client_secret", "26d3bee4fa42c7544f118774154176dc8679bbb0");
        map.put("state", state);
        map.put("code", code);
        map.put("redirect_uri", "http://localhost:9920/account/auto/login");
        // 获取token
        Map resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, Map.class);
        System.out.println(resp);
        HttpHeaders httpheaders = new HttpHeaders();
        // 这种获取token 方式需要改进   需要融合这种方式登录 之后查询 将数据存入mysql中
//        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 token  存到后端   之后重新颁发token
        httpheaders.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);
        System.out.println("exchange.getBody() = " + exchange.getBody());
    }
}
