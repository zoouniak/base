package com.zoouniak.yoursell.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String loginId;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String loginId, String nickname, Role role) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.role = role;
    }
}
