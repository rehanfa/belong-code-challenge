package com.code.challange.proxy.common;

public class JwtToken {
    private String accessToken;
    private String tokenType;

    public JwtToken() {

    }

    public JwtToken(String accessToken) {
        this.setAccessToken(accessToken);
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

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
