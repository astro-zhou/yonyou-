package com.yonyou.iuap.corp.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTokenResponse {

    @JsonProperty("user_access_token")
    private String userAccessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("id_token")
    private String idToken;

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public UserTokenResponse setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public UserTokenResponse setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserTokenResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public UserTokenResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getIdToken() {
        return idToken;
    }

    public UserTokenResponse setIdToken(String idToken) {
        this.idToken = idToken;
        return this;
    }

}
