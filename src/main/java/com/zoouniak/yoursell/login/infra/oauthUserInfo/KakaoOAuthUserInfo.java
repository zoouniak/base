package com.zoouniak.yoursell.login.infra.oauthUserInfo;

public record KakaoOAuthUserInfo(String socialId, String nickname) implements OAuthUserInfo {
    @Override
    public String getLoginId() {
        return socialId();
    }

    @Override
    public String getNickname() {
        return nickname();
    }
}
