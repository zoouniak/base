package com.zoouniak.yoursell.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_GUEST"), USER("ROLE_USER");

    private final String key;
}
