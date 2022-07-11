package com.anzhen.config.oauth2;

import com.anzhen.config.oauth2.github.GitHubAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource private UserDetailsService userDetailsService;
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

  // 静态资源配置
  @Override
  public void configure(WebSecurity web) {
    // swagger2所需要用到的静态资源，允许访问
    web.ignoring()
        .antMatchers(
            "/public/**",
            "/oauth/public/**",
            "/webjars/**",
            "webjars/springfox-swagger-ui/**",
            "webjars/springfox-swagger-ui",
            "/configuration/**",
            "/swagger-ui.html",
            "/static/**",
            "/v2/api-docs**",
            "/swagger-resources/**");
  }
}
