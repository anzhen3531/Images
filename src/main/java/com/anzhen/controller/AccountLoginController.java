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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
     * 账号密码登录
     * @param userForm
     * @param request
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    @PostMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@RequestBody UserForm userForm, HttpServletRequest request)
            throws HttpRequestMethodNotSupportedException {

        String authorization = request.getHeader("Authorization");
        authorization = authorization.substring(6);
        authorization = new String(Base64.getDecoder().decode(authorization.getBytes(StandardCharsets.UTF_8)));
        // 通过符号切割 得出认证字符串
        return this.buildToken(authorization, userForm);
    }

    /**
     * github 第三方接口登录
     *
     * @param code
     * @return 返回token
     */
    @GetMapping("/auto/login")
    public void autoLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        // 先获取用户id
        // 判断是否存在 如果不存在则进行创建  存在则进行调用
        Map<String, String> map = new HashMap<>();
        // 统一封装成为 github 自动登录 分装ch
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

    /**
     * 创建登录请求
     *
     * @param authorization 认证字符串
     * @param userForm      表单登录信息
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    private ResponseEntity<OAuth2AccessToken> buildToken(String authorization, UserForm userForm)
            throws HttpRequestMethodNotSupportedException {
        List<String> userDetail = List.of(authorization.split(":"));
        Map<String, String> parameters = new HashMap<>();
        parameters.put("scope", "all");
        parameters.put("password", userForm.getPassword());
        parameters.put("username", userForm.getUsername());
        parameters.put("grant_type", "password");
        // 创建令牌
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        new User(userDetail.get(0), userDetail.get(1), List.of()), null, List.of());
        return tokenEndpoint.postAccessToken(token, parameters);
    }


}
