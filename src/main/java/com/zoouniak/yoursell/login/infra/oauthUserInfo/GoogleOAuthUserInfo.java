package com.zoouniak.yoursell.login.infra.oauthUserInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleOAuthUserInfo(
        @JsonProperty("id")
        String socialId,
        @JsonProperty("email")
        String email,
        @JsonProperty("name")
        String name
) implements OAuthUserInfo {
    @Override
    public String getLoginId() {
        return socialId();
    }

    @Override
    public String getNickname() {
        return name();
    }


}
