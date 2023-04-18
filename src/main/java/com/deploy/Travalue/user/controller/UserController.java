package com.deploy.Travalue.user.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.NicknameRequestDto;
import com.deploy.Travalue.user.controller.dto.NicknameResponseDto;
import com.deploy.Travalue.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Auth
    @Transactional
    @ApiOperation("닉네임 등록 / 수정")
    @PutMapping("/nickname")
    public ApiResponse<?> updateNickname(
        @Valid @RequestBody final NicknameRequestDto nicknameRequestDto, @UserId Long userId) {
        log.info("userId :" + userId);
        String nickname = nicknameRequestDto.getNickname();
        userService.updateNickname(userId, nickname);
        return ApiResponse.success(SuccessCode.NICKNAME_SUCCESS);
    }

    @ApiOperation("닉네임 중복 체크")
    @GetMapping("/check")
    public ApiResponse<NicknameResponseDto> updateNickname(@Valid @RequestParam String nickname) {
        NicknameResponseDto nicknameResponseDto = userService.checkNickname(nickname);
        return ApiResponse.success(SuccessCode.CHECK_NICKNAME_SUCCESS, nicknameResponseDto);
    }
}
