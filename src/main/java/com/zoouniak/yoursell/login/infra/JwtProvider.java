package com.zoouniak.yoursell.login.infra;

import com.zoouniak.yoursell.login.dto.response.AuthTokens;
import com.zoouniak.yoursell.global.exception.AuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.zoouniak.yoursell.global.exception.ExceptionCode.EXPIRED_TOKEN;
import static com.zoouniak.yoursell.global.exception.ExceptionCode.INVALID_TOKEN;

@Component
public class JwtProvider {
    private static final String EMPTY_SUBJECT = "";
    private static final String PREFIX = "${security.jwt.";
    private final Key secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    public JwtProvider(
            @Value(PREFIX + "secret-key}") String secretKey,
            @Value(PREFIX + "access-expiration-time}") Long accessExpirationTime,
            @Value(PREFIX + "refresh-expiration-time}") Long refreshExpirationTime
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public AuthTokens generateLoginToken(final String userInfo) {
        final String accessToken = createToken(userInfo, accessExpirationTime);
        final String refreshToken = createToken(EMPTY_SUBJECT, refreshExpirationTime);

        return new AuthTokens(accessToken, refreshToken);
    }

    public String createToken(final String userInfo, final Long tokenValidTime) {
        final Date now = new Date();

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(userInfo)
                .signWith(secretKey, signatureAlgorithm)
                .setExpiration(createExpireDate(now, tokenValidTime))
                .compact();
    }

    public String getUserNameFromToken(String accessToken) {
        Claims claims = getClaims(accessToken);

        return claims.getSubject();
    }

    public String resolveAccessToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return "";
    }

    public boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private Map<String, Object> createHeader() {
        return Map.of("alg", "HS256", "typ", "jwt");
    }


    private Date createExpireDate(final Date now, final Long expirationTime) {
        return new Date(now.getTime() + expirationTime);
    }

    private Claims getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new AuthException(INVALID_TOKEN);
        }
    }
}
