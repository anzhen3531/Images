package com.anzhen.config;

import com.anzhen.common.ResponseMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;

@Configuration
@EnableResourceServer
/**
 * 资源服务器配置
 */
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                // 关闭默认登录页面
                .authorizeRequests()
                .antMatchers("/oauth/**", "/account/**").permitAll()
                .anyRequest().authenticated() // 任何请求都要认证
                .and()
                .logout().permitAll()
                .and().csrf().disable()
                .exceptionHandling()
                // 配置异常处理端点
                .authenticationEntryPoint((req, resp, authException) -> {
                    resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    resp.setCharacterEncoding("UTF-8");
                    resp.setStatus(401);
                    PrintWriter out = resp.getWriter();
                    ResponseMsg responseMsg = ResponseMsg.error("访问失败!");
                    if (authException instanceof InsufficientAuthenticationException) {
                        responseMsg.setMsg("请求失败，请联系管理员!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(responseMsg));
                    out.flush();
                    out.close();
                });
    }
}
