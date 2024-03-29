package com.anzhen.config.oauth2.github;

import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anzhen.entity.AUser;
import com.anzhen.service.AUserService;

import cn.hutool.core.util.ObjectUtil;

public class GitHubAuthenticationProvider implements AuthenticationProvider {

    @Resource
    GitHubAutoLogin gitHubAutoLogin;
    @Resource
    AUserService aUserService;
    @Resource
    UserDetailsService userDetailsService;

    /**
     * 自定义github 授权码认证方式
     *
     * @param authentication the authentication request object.
     * @return 返回认证对象
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取code
        GithubAuthenticationToken authenticationToken = (GithubAuthenticationToken)authentication;
        String code = (String)authenticationToken.getPrincipal();

        // 编写github 登录sdk 直接调用即可
        AUser aUser = gitHubAutoLogin.getGitHubUsernameByCode(code);
        if (ObjectUtil.isNotNull(aUser)) {
            AUser userByName = aUserService.findByUsername(aUser.getUsername());
            if (ObjectUtil.isNull(userByName)) {
                aUserService.create(aUser);
            }
        } else {
            throw new UsernameNotFoundException("没有查找到账号");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(aUser.getUsername());
        GithubAuthenticationToken result = new GithubAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * 支持什么方式
     *
     * @return 返回是否支持这种方式
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return GithubAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
