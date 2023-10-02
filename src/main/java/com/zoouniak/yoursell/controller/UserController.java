package com.zoouniak.yoursell.controller;

import com.zoouniak.yoursell.dto.LoginDTO;
import com.zoouniak.yoursell.dto.AuthenticationResponse;
import com.zoouniak.yoursell.dto.UserSignupDTO;
import com.zoouniak.yoursell.exception.DuplicatedUserException;
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
    public ResponseEntity signup(@RequestBody UserSignupDTO signupDTO) {
        try{
            return ResponseEntity.ok(userService.signup(signupDTO));
        }catch (DuplicatedUserException e){
            return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody LoginDTO signupDTO) {
        return ResponseEntity.ok(userService.authenticate(signupDTO));
    }


}
