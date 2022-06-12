package com.anzhen.config.oauth2.github;

import com.anzhen.entity.AUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({GithubProperties.class})
public class GitHubAutoLogin {
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    @Resource
    RestTemplate restTemplate;
    @Resource
    GithubProperties githubProperties;

    public AUser getGitHubUsernameByCode(String code) {
        Map<String, String> map = new HashMap<>();
        // 统一封装成为github登录
        map.put("client_id", githubProperties.getClientId());
        map.put("client_secret", githubProperties.getClientSecret());
        map.put("state", "image");
        map.put("code", code);
        // 应该配置成为配置文件
        map.put("redirect_uri", githubProperties.getRedirectUri());
        // 获取token
        Map resp = restTemplate.postForObject(GITHUB_TOKEN_URL, map, Map.class);
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
