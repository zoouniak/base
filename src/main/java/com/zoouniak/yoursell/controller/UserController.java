package com.zoouniak.yoursell.controller;

import com.zoouniak.yoursell.dto.request.LoginRequest;
import com.zoouniak.yoursell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<?> login(
            @PathVariable(name = "provider") String provider,
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().build();
    }


}
