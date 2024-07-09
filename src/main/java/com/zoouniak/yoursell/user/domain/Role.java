package com.zoouniak.yoursell.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_GUEST"), GENERAL("ROLE_USER");

    private final String role;
}
