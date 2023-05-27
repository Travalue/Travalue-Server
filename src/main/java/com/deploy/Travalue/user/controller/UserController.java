package com.deploy.Travalue.user.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.MyPageResponseDto;
import com.deploy.Travalue.user.controller.dto.NicknameRequestDto;
import com.deploy.Travalue.user.controller.dto.NicknameResponseDto;
import com.deploy.Travalue.user.controller.dto.UserBlockRequestDto;
import com.deploy.Travalue.user.service.UserService;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation("마이페이지 조회")
    @GetMapping("/")
    public ApiResponse<?> getMyPage(@UserId Long userId) {
        log.info("userId :" + userId);
        MyPageResponseDto myPageResponseDto = userService.getMyPage(userId);
        return ApiResponse.success(SuccessCode.GET_MY_PAGE_SUCCESS, myPageResponseDto);
    }

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

    @Auth
    @PostMapping("/block")
    public ApiResponse<?> userBlock(@UserId Long userId,
                                    @RequestBody UserBlockRequestDto userBlockRequestDto) {
        log.info("유저 차단  userId : " + userId + " userBolckRequestDto : " + userBlockRequestDto.toString());

        userService.blockUser(userId, userBlockRequestDto);

        return ApiResponse.success(SuccessCode.USER_BLOCK_SUCCESS, null);
    }
}
