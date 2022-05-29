package com.anzhen.config.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 配置认证服务器
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
// 需要全部参数构造
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    @Resource(name = "tokenStore")
    private TokenStore tokenStore;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用数据库存储Client认证信息
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 开启/oauth/token_key验证端口认证权限访问
                .tokenKeyAccess("isAuthenticated()")
                //  开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                //允许表单认证 在授权码模式下会导致无法根据code获取token&emsp;
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.
                authenticationManager(authenticationManager).
                userDetailsService(userDetailsService).
                tokenStore(tokenStore);
    }

}
