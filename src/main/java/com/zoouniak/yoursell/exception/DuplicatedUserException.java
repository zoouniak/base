package com.zoouniak.yoursell.exception;

public class DuplicatedUserException extends RuntimeException{
    public DuplicatedUserException(){
        super("이미 존재하는 이메일입니다.");
    }
}
