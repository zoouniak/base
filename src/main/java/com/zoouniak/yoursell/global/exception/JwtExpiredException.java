package com.zoouniak.yoursell.global.exception;

public class JwtExpiredException extends AuthException{
    public JwtExpiredException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
