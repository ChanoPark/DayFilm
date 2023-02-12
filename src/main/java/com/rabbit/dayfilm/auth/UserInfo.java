package com.rabbit.dayfilm.auth;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash("user")
public class UserInfo implements Serializable {

    @Id
    private String email;
    private String pw;
    private String refreshToken;
    private String nickname;
    private Role role;
//    @TimeToLive(unit = TimeUnit.DAYS)
//    private Long ttl;
    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
