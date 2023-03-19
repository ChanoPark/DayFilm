package com.rabbit.dayfilm.auth.controller;

import com.rabbit.dayfilm.auth.dto.AuthResDto;
import com.rabbit.dayfilm.auth.dto.LoginInfo;
import com.rabbit.dayfilm.auth.dto.SignReqDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "회원가입&로그인")
@AllArgsConstructor
public class AuthController {
    @PostMapping("/sign/user-example")
    @Operation(summary = "일반 회원 가입", description = "일반 회원 가입 모델입니다.\n엔드포인트 뒤에 '-example' 떼고 요청하셔야해요.")
    public AuthResDto exampleSignUser(@RequestBody SignReqDto.SignUser request) {
        return null;
    }

    @PostMapping("/sign/store-example")
    @Operation(summary = "가게 회원 가입", description = "가게 회원 가입 모델입니다.\n은행은 우선 IBK로 고정해주시고, 계좌번호도 숫자 아무거나 넣어주시면 될 것 같습니다.\n엔드포인트 뒤에 '-example' 떼고 요청하셔야해요.")
    public AuthResDto exampleSignStore(@RequestBody SignReqDto.SignStore request) {
        return null;
    }

    @PostMapping("/login-example")
    @Operation(summary = "로그인", description = "로그인 모델입니다.\n로그인을 하게 되면 토큰이 새로 발급됩니다.\n엔드포인트 뒤에 '-example' 떼고 요청하셔야해요.")
    public AuthResDto exampleLogin(@RequestBody LoginInfo request) {
        return null;
    }

    @PostMapping("/reissue-example")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 모델입니다.\nRequest header의 Refresh-Token 키 값에 리프레시 토큰 넣어주시면 됩니다.\nResponse도 헤더의 Authorization에 엑세스 토큰, Refresh-Token에 리프레시 토큰이 들어갑니다.\n엔드포인트 뒤에 '-example' 떼고 요청하셔야해요.")
    public void exampleReissue() {

    }
}
