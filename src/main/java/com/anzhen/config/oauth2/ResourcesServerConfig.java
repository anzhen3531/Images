package com.anzhen.config.oauth2;

import com.anzhen.config.CorsConfig;
import com.anzhen.config.oauth2.handle.AuthExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

/** 资源服务器配置 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 开启全局方式注解鉴权
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

  private final RedisTokenStore redisTokenStore;
  private final AuthExceptionHandler authExceptionHandler;
  private final CorsConfig config;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    // 状态
    resources
        .stateless(true)
        .accessDeniedHandler(authExceptionHandler)
        .authenticationEntryPoint(authExceptionHandler);
    // 设置token存储
    resources.tokenStore(redisTokenStore);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 请求权限配置
    http.authorizeRequests()
        // 下边的路径放行,不需要经过认证
        .antMatchers("/account/**", "/user/**", "/image/main/*")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/oauth/**") // 配置跨域
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .cors()
        .and()
        .csrf()
        .disable()
        .addFilterBefore(config, WebAsyncManagerIntegrationFilter.class);
  }
}
