package com.zoouniak.yoursell.global.exception;

public class AuthException extends BaseException{
    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
