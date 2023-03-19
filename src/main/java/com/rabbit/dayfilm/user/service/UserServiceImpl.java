package com.rabbit.dayfilm.user.service;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.order.repository.OrderRepository;
import com.rabbit.dayfilm.user.dto.OrderListResDto;
import com.rabbit.dayfilm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public CodeSet checkNickname(String nickname) {
        if (userRepository.countUserByNickname(nickname) > 0) return CodeSet.DUPLICATE_NICKNAME;
        else return CodeSet.OK;
    }

    @Override
    public OrderListResDto getOrderList(Long userId, Pageable pageable) {
        Page<OrderListResDto.OrderList> content = orderRepository.getOrderList(userId, false, pageable);
        return new OrderListResDto(content.getTotalPages(), content.isLast(), content.getContent());
    }
}
