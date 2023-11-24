package com.example.projectboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {

    private String secretKey;

    private Long tokenExpiredTimeMs;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getTokenExpiredTimeMs() {
        return tokenExpiredTimeMs;
    }

    public void setTokenExpiredTimeMs(Long tokenExpiredTimeMs) {
        this.tokenExpiredTimeMs = tokenExpiredTimeMs;
    }
}
