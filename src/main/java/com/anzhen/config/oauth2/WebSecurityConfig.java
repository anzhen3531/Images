package com.anzhen.config.oauth2;

import com.anzhen.config.CorsConfig;
import com.anzhen.config.oauth2.github.GitHubAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource private UserDetailsService userDetailsService;
  @Resource private CorsConfig config;
  /**
   * 认证管理对象
   *
   * @return
   * @throws Exception
   */
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 添加provider 配置
   *
   * @param auth
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(gitHubAuthenticationProvider());
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  /**
   * 用户名密码认证授权提供者
   *
   * @return
   */
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    provider.setHideUserNotFoundExceptions(false); // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
    return provider;
  }

  /**
   * 微信认证授权提供者
   *
   * @return
   */
  @Bean
  public GitHubAuthenticationProvider gitHubAuthenticationProvider() {
    return new GitHubAuthenticationProvider();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 请求权限配置
    http.authorizeRequests()
        // 下边的路径放行,不需要经过认证
        .antMatchers("/account/**", "/user/**", "/image/main/*", "/oauth/**")
        .permitAll()
        .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs")
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
