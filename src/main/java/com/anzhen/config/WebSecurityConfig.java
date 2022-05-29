package com.anzhen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    /**
//     * 配置那些需要放行
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.formLogin().disable()
//                // 关闭默认登录页面
//                .authorizeRequests().antMatchers("/account/auto/logout", "/account/login").permitAll().anyRequest().authenticated() // 任何请求都要认证
//                .and().logout().permitAll().and().csrf().disable().exceptionHandling()
//                // 配置异常处理端点
//                .authenticationEntryPoint((req, resp, authException) -> {
//                    resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    resp.setCharacterEncoding("UTF-8");
//                    resp.setStatus(401);
//                    PrintWriter out = resp.getWriter();
//                    ResponseMsg responseMsg = ResponseMsg.error("访问失败!");
//                    if (authException instanceof InsufficientAuthenticationException) {
//                        responseMsg.setMsg("请求失败，请联系管理员!");
//                    }
//                    out.write(new ObjectMapper().writeValueAsString(responseMsg));
//                    out.flush();
//                    out.close();
//                });
//    }

    /**
     * 配置放行的路径
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        // 静态资源放行
        web.ignoring().antMatchers(
                "/account/auto/login",
                "/v2/api-docs",
                "/webjars/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html");
    }

}
