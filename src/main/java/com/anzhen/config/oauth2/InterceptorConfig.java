package com.anzhen.config.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.anzhen.config.oauth2.filter.AUserContextInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAUserContextInterceptor()).addPathPatterns("/**");
    }

    @Bean
    protected HandlerInterceptor getAUserContextInterceptor() {
        return new AUserContextInterceptor();
    }
}
