package com.rabbit.dayfilm.auth.service;

import com.rabbit.dayfilm.auth.UserInfo;
import com.rabbit.dayfilm.auth.dto.SignReqDto;

public interface AuthService {
    void signStore(SignReqDto.SignStore request, String refreshToken);
    void signUser(SignReqDto.SignUser request, String refreshToken);
    UserInfo getUserClaim(String token);
}
