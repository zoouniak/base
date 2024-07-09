package com.zoouniak.yoursell.login.service;

import com.zoouniak.yoursell.login.dto.response.AuthTokens;
import com.zoouniak.yoursell.login.dto.request.LoginRequest;
import com.zoouniak.yoursell.login.infra.oauthUserInfo.OAuthUserInfo;
import com.zoouniak.yoursell.user.domain.User;
import com.zoouniak.yoursell.login.infra.oauthProvider.OAuthProvider;
import com.zoouniak.yoursell.login.infra.oauthProvider.OAuthProviders;
import com.zoouniak.yoursell.user.domain.repository.UserRepository;
import com.zoouniak.yoursell.login.infra.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zoouniak.yoursell.user.domain.Role.GENERAL;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final OAuthProviders oAuthProviders;
    private final JwtProvider jwtProvider;

    public AuthTokens login(final String provider, final LoginRequest loginRequest) {
        OAuthProvider strategy = oAuthProviders.mapping(provider);
        OAuthUserInfo userInfo = strategy.getUserInfo(loginRequest.code());
        // 획득한 사용자 정보 db에 조회
        User loginUser = findOrCreateUser(
                userInfo.getLoginId(),
                userInfo.getNickname()
        );
        // accesstoken, refreshtoken 발급
        AuthTokens loginTokens = jwtProvider.generateLoginToken(String.valueOf(loginUser.getId()));
        // todo refreshtoken저장

        return loginTokens;
    }

    private User findOrCreateUser(final String loginId, final String nickname) {
        return userRepository.findByLoginId(loginId)
                .orElseGet(() -> createUser(loginId, nickname));
    }

    private User createUser(final String loginId, final String nickname) {
        return userRepository.save(new User(loginId, nickname, GENERAL));
    }
}
