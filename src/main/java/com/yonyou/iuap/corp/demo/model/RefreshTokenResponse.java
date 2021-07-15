package com.yonyou.iuap.corp.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenResponse {

    @JsonProperty("user_access_token")
    private String userAccessToken;

    private long expired;

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

}
