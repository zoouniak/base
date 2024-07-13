package com.zoouniak.yoursell.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@AllArgsConstructor
@Getter
public enum ExceptionCode {
    NOT_SUPPORT_PROVIDER(101, BAD_REQUEST, "지원하지 않는 Provider입니다."),
    INVALID_AUTHORIZE_CODE(102, BAD_REQUEST, "code가 유효하지 않습니다."),
    FAIL_GET_USERINFO(103, BAD_REQUEST, "사용자 정보를 가져오는 데 실패하였습니다."),
    INVALID_REQUEST(104, BAD_REQUEST, "유효하지 않은 요청입니다."),
    EXPIRED_ACCESS_TOKEN(104, BAD_REQUEST, " 어세스 토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(104, BAD_REQUEST, "리프레시 토큰이 만료되었습니다."),
    INVALID_TOKEN(105, BAD_REQUEST, "유효하지 않는 토큰입니다."),
    INVALID_AUTHORITY(108,BAD_REQUEST,"권한이 없습니다."),
    INVALID_REFRESH_TOKEN(106, BAD_REQUEST, "유효하지 않는 리프레시 토큰입니다."),
    FAIL_TO_VALIDATE(107, BAD_REQUEST, "토큰 검증에 실패하였습니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
