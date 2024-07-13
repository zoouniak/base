package com.zoouniak.yoursell.auth.domain;

import com.zoouniak.yoursell.global.exception.AuthException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.zoouniak.yoursell.global.exception.ExceptionCode.INVALID_AUTHORITY;

@Aspect
@Component
public class MemberOnlyChecker {

    @Before("@annotation(com.zoouniak.yoursell.auth.domain.MemberOnly)")
    public void check(final JoinPoint joinPoint) {

        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isMember)
                .findFirst()
                .orElseThrow(() -> new AuthException(INVALID_AUTHORITY));
    }
}