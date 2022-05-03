package com.anzhen.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinioConfiguration {
    @Bean
    public MinioClient minioClient(@Autowired MinioProperties minioProperties) {
        return MinioClient
                .builder()
                .endpoint(minioProperties.getEndpoint() + ":" + minioProperties.getPort())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
}
