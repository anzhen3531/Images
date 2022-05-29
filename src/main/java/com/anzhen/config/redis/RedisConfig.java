package com.anzhen.config.redis;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        // 用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer); // key
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 指定要序列化的域(field,get,set)，访问修饰符(public,private,protected)
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); //value
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
}
