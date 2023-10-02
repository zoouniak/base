package com.zoouniak.yoursell.controller;

import com.zoouniak.yoursell.dto.LoginDTO;
import com.zoouniak.yoursell.dto.AuthenticationResponse;
import com.zoouniak.yoursell.dto.UserSignupDTO;
import com.zoouniak.yoursell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody UserSignupDTO signupDTO) {
        return ResponseEntity.ok(userService.signup(signupDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody LoginDTO signupDTO) {
        return ResponseEntity.ok(userService.authenticate(signupDTO));
    }


}
