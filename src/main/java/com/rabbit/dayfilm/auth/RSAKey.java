package com.rabbit.dayfilm.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RSAKey {
    private String privateKey;
    private String publicKey;
}
