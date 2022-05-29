package com.anzhen.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

@Configuration
public class RedisTokenStoreConfig {
    @Resource
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
