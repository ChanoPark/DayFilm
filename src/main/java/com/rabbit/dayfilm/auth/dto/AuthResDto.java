package com.rabbit.dayfilm.auth.dto;

import com.rabbit.dayfilm.auth.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResDto {

    @ApiModelProperty(value="닉네임", example="테스트닉네임1")
    private String nickname;

    @ApiModelProperty(value="권한\nUSER:일반 회원\nSTORE:가게\nSELLER:판매 권한을 받은 회원", example="USER")
    private Role role;

    @ApiModelProperty(value="PK", example="1")
    private Long pk;

    public AuthResDto(String nickname, Role role) {
        this.nickname = nickname;
        this.role = role;
    }
}
