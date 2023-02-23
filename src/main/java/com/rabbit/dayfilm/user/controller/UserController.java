package com.rabbit.dayfilm.user.controller;

import com.rabbit.dayfilm.user.service.UserService;
import com.rabbit.dayfilm.common.EndPoint;
import com.rabbit.dayfilm.common.response.SuccessResponse;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "회원&가게")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(EndPoint.CHECK_NICKNAME)
    public ResponseEntity<SuccessResponse> checkNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok().body(new SuccessResponse(userService.checkNickname(nickname)));
    }
}
