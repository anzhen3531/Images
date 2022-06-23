package com.anzhen.config.oauth2.github;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(GithubProperties.PREFIX)
public class GithubProperties {

    static final String PREFIX = "github";

    private String clientId;

    private String clientSecret;

        private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
