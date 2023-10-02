package com.zoouniak.controller;

import com.zoouniak.dto.AuthenticationRequest;
import com.zoouniak.dto.AuthenticationResponse;
import com.zoouniak.dto.UserSignupDTO;
import com.zoouniak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(UserSignupDTO signupDTO) {
        return ResponseEntity.ok(userService.signup(signupDTO));

    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(AuthenticationRequest signupDTO) {
        return ResponseEntity.ok(userService.authenticate(signupDTO));

    }
}
