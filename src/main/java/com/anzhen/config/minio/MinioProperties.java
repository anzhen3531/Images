package com.anzhen.config.minio;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(MinioProperties.PREFIX)
public class MinioProperties {

    static final String PREFIX = "minio";

    private String endpoint;

    private Integer port;

    private String accessKey;

    private String secretKey;

    private String bucket;

    public static String getPREFIX() {
        return PREFIX;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
