package com.rabbit.dayfilm.auth;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash("user")
public class UserInfo {

    @Id
    private String email;
    private String pw;
    private String refreshToken;
//    @TimeToLive(unit = TimeUnit.DAYS)
//    private Long ttl;
    //로그인(회원인증 -> 이메일:비번)
    //재발급(토큰발급 -> 토큰:이메일)ㅇ
}
