package com.anzhen.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.anzhen.mapper")
public class MybatisPlusConfiguration {
}
