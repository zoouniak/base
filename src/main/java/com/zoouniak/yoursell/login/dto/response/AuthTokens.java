package com.zoouniak.yoursell.login.dto.response;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {
}
