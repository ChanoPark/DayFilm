package com.rabbit.dayfilm.auth.dto;

import com.rabbit.dayfilm.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResDto {
    private String nickname;
    private Role role;
}
