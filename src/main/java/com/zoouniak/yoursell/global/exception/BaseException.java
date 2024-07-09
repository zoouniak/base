package com.zoouniak.yoursell.global.exception;

public class BaseException extends RuntimeException{

    public BaseException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
    }
}
