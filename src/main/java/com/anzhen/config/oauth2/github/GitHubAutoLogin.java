package com.anzhen.config.oauth2.github;

import com.anzhen.entity.AUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GitHubAutoLogin {

    @Resource
    RestTemplate restTemplate;

    public AUser getGitHubUsernameByCode(String code) {
        Map<String, String> map = new HashMap<>();
        // 统一封装成为 github 自动登录 分装ch
        map.put("client_id", "1c5c71a9fc77bea14869");
        map.put("client_secret", "26d3bee4fa42c7544f118774154176dc8679bbb0");
        map.put("state", "image");
        map.put("code", code);
        map.put("redirect_uri", "http://localhost:9920/account/auto/login");
        // 获取token
        Map resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, Map.class);
        System.out.println(resp);
        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);
        log.info("返回得到的github Json信息 {} ", exchange.getBody());
        Map body = exchange.getBody();
        AUser aUser = new AUser();
        aUser.setUsername((String) body.get("login"));
        aUser.setName((String) body.get("name"));
        aUser.setEmail((String) body.get("email"));
        return aUser;
    }
}
