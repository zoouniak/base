package com.zoouniak.yoursell.login.service;

import com.zoouniak.yoursell.global.exception.AuthException;
import com.zoouniak.yoursell.global.exception.ExceptionCode;
import com.zoouniak.yoursell.login.domain.RefreshToken;
import com.zoouniak.yoursell.login.domain.repository.RefreshTokenRepository;
import com.zoouniak.yoursell.login.dto.response.AccessTokenResponse;
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

import static com.zoouniak.yoursell.global.exception.ExceptionCode.FAIL_TO_VALIDATE;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuthProviders oAuthProviders;
    private final JwtProvider jwtProvider;

    public AuthTokens login(final String provider, final LoginRequest loginRequest) {
        final OAuthProvider strategy = oAuthProviders.mapping(provider);
        final OAuthUserInfo userInfo = strategy.getUserInfo(loginRequest.code());
        // 획득한 사용자 정보 db에 조회
        User loginUser = findOrCreateUser(
                userInfo.getLoginId(),
                userInfo.getNickname()
        );
        // accesstoken, refreshtoken 발급
        final AuthTokens loginTokens = jwtProvider.generateLoginToken(String.valueOf(loginUser.getId()));
        // refreshtoken 저장
        refreshTokenRepository.save(new RefreshToken(loginTokens.refreshToken(), loginUser.getId()));

        return loginTokens;
    }

    private User findOrCreateUser(final String loginId, final String nickname) {
        return userRepository.findByLoginId(loginId)
                .orElseGet(() -> createUser(loginId, nickname));
    }

    private User createUser(final String loginId, final String nickname) {
        return userRepository.save(new User(loginId, nickname));
    }

    public AccessTokenResponse extend(String authorizeHeader, String refreshTokenReq) {
        final String accessToken = jwtProvider.resolveAccessToken(authorizeHeader);
        // isInvalidAccessToken && isValidRefreshToken -> then re-issue accessToken
        if (jwtProvider.isInvalidAccessTokenAndValidRefreshToken(accessToken, refreshTokenReq)) {
            RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenReq).orElseThrow(
                    () -> new AuthException(ExceptionCode.INVALID_REFRESH_TOKEN)
            );
            final String regenerateAccessToken = jwtProvider.generateAccessToken(refreshToken.getLoginId().toString());

            return new AccessTokenResponse(regenerateAccessToken);
        }

        // isValidAccessToken && isValidRefreshToken then return accessToken
        else if (jwtProvider.isValidAccessTokenAndValidRefreshToken(accessToken, refreshTokenReq)) {
            return new AccessTokenResponse(accessToken);
        }

        throw new AuthException(FAIL_TO_VALIDATE);
    }


}
