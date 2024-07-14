package com.zoouniak.yoursell.global.exception;

public class InvalidJwtException extends AuthException{
    public InvalidJwtException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
