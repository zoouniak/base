package com.zoouniak.yoursell.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleException(final AuthException e) {
        log.error(e.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getCode(), e.getMessage());

        return ResponseEntity.status(400).body(exceptionResponse);
    }
}
