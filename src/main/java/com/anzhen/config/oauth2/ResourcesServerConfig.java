package com.anzhen.config.oauth2;

import com.anzhen.config.oauth2.handle.AuthExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
/**
 * 资源服务器配置
 */ public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    private final RedisTokenStore redisTokenStore;
    private final AuthExceptionHandler authExceptionHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //状态
        resources.stateless(true)
                .accessDeniedHandler(authExceptionHandler)
                .authenticationEntryPoint(authExceptionHandler);
        //设置token存储
        resources.tokenStore(redisTokenStore);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //请求权限配置
        http.authorizeRequests()
                //下边的路径放行,不需要经过认证
                .antMatchers("/actuator/health", "/oauth/**", "/account/**", "/user/**").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**").permitAll()
                //其余接口没有角色限制，但需要经过认证，只要携带token就可以放行
                .antMatchers("/dictionary/**").permitAll()
                .anyRequest()
                .authenticated();
    }
}
