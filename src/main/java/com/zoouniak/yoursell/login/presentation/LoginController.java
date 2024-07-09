package com.zoouniak.yoursell.login.presentation;

import com.zoouniak.yoursell.login.dto.response.AccessTokenResponse;
import com.zoouniak.yoursell.login.dto.response.AuthTokens;
import com.zoouniak.yoursell.login.dto.request.LoginRequest;
import com.zoouniak.yoursell.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private static final int COOKIE_AGE_SECONDS = 604800;
    private final LoginService loginService;

    /*
     * authorize code와 함께 로그인 요청
     */
    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable(name = "provider") final String provider,
            @RequestBody final LoginRequest loginRequest,
            HttpServletResponse response) {
        AuthTokens tokens = loginService.login(provider, loginRequest);

        response.addHeader(SET_COOKIE, makeCookie(tokens.refreshToken()).toString());
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(tokens.accessToken()));
    }

    private static ResponseCookie makeCookie(final String refreshToken) {
        return ResponseCookie.from("refresh-token", refreshToken)
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }


}
