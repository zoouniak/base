package com.zoouniak.yoursell.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupDTO {
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
}
