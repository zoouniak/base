package com.zoouniak.yoursell.login.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoouniak.yoursell.global.ControllerTest;
import com.zoouniak.yoursell.login.dto.request.LoginRequest;
import com.zoouniak.yoursell.login.dto.response.AccessTokenResponse;
import com.zoouniak.yoursell.login.dto.response.AuthTokens;
import com.zoouniak.yoursell.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest extends ControllerTest {
    private static final String REFRESH_TOKEN = "refresh-token";
    private static final String ACCESS_TOKEN = "access-token";
    private static final String ACCESS_TOKEN_REQ = "access";
    private static final String REFRESH_TOKEN_REQ = "refresh";
    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void 로그인_한다() throws Exception {
        // assert
        final LoginRequest req = new LoginRequest("code");
        final AuthTokens tokens = new AuthTokens(ACCESS_TOKEN_REQ, REFRESH_TOKEN_REQ);

        given(loginService.login(anyString(), any())).willReturn(tokens);

        // act
        ResultActions resultActions = mockMvc.perform(post("/login/{provider}", "kakao")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        );
        // assert
        resultActions.andExpect(status().isCreated())
                .andExpect(cookie().exists(REFRESH_TOKEN))
                .andExpect(header().exists(ACCESS_TOKEN));
    }

    @Test
    void 리프레시토큰을_통해_로그인을_연장한다() throws Exception {
        // assert
        final String refreshTokenReq = REFRESH_TOKEN_REQ;
        final String accessTokenReq = "Bearer " + ACCESS_TOKEN_REQ;
        AccessTokenResponse newAccessToken = new AccessTokenResponse("newAccessToken");
        when(loginService.extend(accessTokenReq, refreshTokenReq)).thenReturn(newAccessToken);

        // act
        ResultActions resultActions = mockMvc.perform(post("/login/extend")
                .contentType(APPLICATION_JSON)
                .cookie(new Cookie(REFRESH_TOKEN, refreshTokenReq))
                .header(HttpHeaders.AUTHORIZATION, accessTokenReq)
        );

        // assert
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists(ACCESS_TOKEN));
    }
}