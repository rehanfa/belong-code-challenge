package com.code.challange.security.common;

public class JwtToken {
    private final String tokenType = "Bearer";
    private String accessToken;

    public JwtToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
