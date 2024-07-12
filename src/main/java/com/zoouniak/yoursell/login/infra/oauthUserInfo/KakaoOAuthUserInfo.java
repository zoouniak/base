package com.zoouniak.yoursell.login.infra.oauthUserInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoOAuthUserInfo(
        @JsonProperty("id")
        String socialId,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) implements OAuthUserInfo {
    @Override
    public String getLoginId() {
        return socialId();
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname();
    }

    public record KakaoAccount(
            Profile profile
    ) {

    }

    public record Profile(
            @JsonProperty("nickname")
            String nickname
    ) {
        public String nickname() {
            return nickname;
        }
    }
}
