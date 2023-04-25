package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.user.dto.CancelOrderDto;
import com.rabbit.dayfilm.user.dto.OrderListResDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    CodeSet checkNickname(String nickname);
    OrderListResDto getOrderList(Long userId, Boolean isCanceled, Pageable pageable);
    CodeSet requestCancelOrder(CancelOrderDto request);
}
