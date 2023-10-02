package com.zoouniak.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public ResponseEntity home() {
        log.info("homehome");
        return ResponseEntity.ok().build();
    }
}
