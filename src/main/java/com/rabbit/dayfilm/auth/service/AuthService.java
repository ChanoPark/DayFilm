package com.rabbit.dayfilm.auth.service;

import com.rabbit.dayfilm.auth.dto.SignReqDto;

public interface AuthService {
    boolean checkBusinessNumber(Long bno);
    void signStore(SignReqDto.SignStore request);
}
