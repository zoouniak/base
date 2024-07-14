package com.zoouniak.yoursell.login.infra;

import com.zoouniak.yoursell.login.dto.response.AuthTokens;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

class JwtProviderTest {

    private final JwtProvider jwtProvider = new JwtProvider(
            "IHspE3C2/qB/KMVJNEd1+gKV13QiP1N/ehQGzjsBWIE=",
            18000000L,
            180000000L
    );

    @Test
    void 어세스토큰과_리프레시토큰을_생성한다() {
        // assert
        String userId = "1";

        // act
        AuthTokens authTokens = jwtProvider.generateLoginToken(userId);

        // assert
        Assertions.assertEquals("1", jwtProvider.getUserIdFromToken(authTokens.accessToken()));
        Assertions.assertNull(jwtProvider.getUserIdFromToken(authTokens.refreshToken()));
    }

    @Test
    void 헤더에서_토큰을_추출한다() {
        // assert
        String authHeader = "Bearer validToken123";
        // act
        String accessToken = jwtProvider.resolveAccessToken(authHeader);
        // assert
        Assertions.assertEquals("validToken123", accessToken);
    }

    @Test
    void 만료된_어세스토큰의_만료를_검증한다() {
        // assert
        // access token, refresh token 생성
        String accessToken = createToken("1", 0L);
        String refreshToken = createToken(null, 1800000L);
        // act
        boolean result = jwtProvider.isInvalidAccessTokenAndValidRefreshToken(accessToken, refreshToken);
        // assert true 확인
        Assertions.assertTrue(result);
    }

    @Test
    void 만료되지않은_어세스토큰을_만료를_검증한다() {
        // assert
        String accessToken = createToken("1", 180000L);
        String refreshToken = createToken(null, 1800000L);
        // act
        boolean result = jwtProvider.isInvalidAccessTokenAndValidRefreshToken(accessToken, refreshToken);
        // assert
        Assertions.assertFalse(result);
    }

    @Test
    void 만료되지않은_어세스토큰의_유효를_검증한다() {
        // assert
        String accessToken = createToken("1", 180000L);
        String refreshToken = createToken(null, 1800000L);
        // act
        boolean result = jwtProvider.isValidAccessTokenAndValidRefreshToken(accessToken, refreshToken);
        // assert
        Assertions.assertTrue(result);
    }

    @Test
    void 만료된_어세스토큰의_유효를_검증한다() {
        // assert
        String accessToken = createToken("1", 0L);
        String refreshToken = createToken(null, 1800000L);
        // act
        boolean result = jwtProvider.isValidAccessTokenAndValidRefreshToken(accessToken, refreshToken);
        // assert
        Assertions.assertFalse(result);
    }

    private String createToken(String subject, Long tokenValidTime) {
        Date now = new Date();
        String testSecretKey = "IHspE3C2/qB/KMVJNEd1+gKV13QiP1N/ehQGzjsBWIE=";
        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(subject)
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(testSecretKey)), SignatureAlgorithm.HS256)
                .setExpiration(createExpireDate(now, tokenValidTime))
                .compact();

    }

    private Date createExpireDate(final Date now, final Long expirationTime) {
        return new Date(now.getTime() + expirationTime);
    }

    private Map<String, Object> createHeader() {
        return Map.of("alg", "HS256", "typ", "jwt");
    }
}