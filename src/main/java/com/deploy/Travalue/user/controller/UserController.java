package com.deploy.Travalue.user.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.*;
import com.deploy.Travalue.user.service.UserService;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Auth
    @ApiOperation("마이페이지 조회")
    @GetMapping("/{pageOwnerUserId}")
    public ApiResponse<MyPageResponseDto> getMyPage(@UserId Long userId, Long pageOwnerUserId) {
        log.info("userId :" + userId + " pageOwnerUserId : " + pageOwnerUserId);
        MyPageResponseDto myPageResponseDto = userService.getMyPage(userId, pageOwnerUserId);
        return ApiResponse.success(SuccessCode.GET_MY_PAGE_SUCCESS, myPageResponseDto);
    }

    @Auth
    @PatchMapping()
    @ApiOperation("마이페이지 수정")
    public ApiResponse<NicknameResponseDto> updateProfile(@Valid @RequestBody final UpdateProfileRequestDto updateProfileRequestDto, @UserId Long userId) {
        userService.updateProfile(updateProfileRequestDto, userId);
        return ApiResponse.success(SuccessCode.UPDATE_PROFILE_SUCCESS);
    }

    @Auth
    @Transactional
    @PutMapping("/nickname")
    @ApiOperation("닉네임 등록 / 수정")
    public ApiResponse updateNickname(
            @Valid @RequestBody final NicknameRequestDto nicknameRequestDto, @UserId Long userId) {
        log.info("userId :" + userId);
        String nickname = nicknameRequestDto.getNickname();
        userService.updateNickname(userId, nickname);
        return ApiResponse.success(SuccessCode.NICKNAME_SUCCESS);
    }

    @GetMapping("/check")
    @ApiOperation("닉네임 중복 체크")
    public ApiResponse<NicknameResponseDto> updateNickname(@Valid @RequestParam String nickname) {
        NicknameResponseDto nicknameResponseDto = userService.checkNickname(nickname);
        return ApiResponse.success(SuccessCode.CHECK_NICKNAME_SUCCESS, nicknameResponseDto);
    }

    @Auth
    @PostMapping("/block/{userId}")
    @ApiOperation("유저 차단")
    public ApiResponse<?> blockUser(@UserId Long myId, @PathVariable Long userId) {
        userService.blockUser(myId, userId);
        return ApiResponse.success(SuccessCode.USER_BLOCK_SUCCESS);
    }

    @Auth
    @DeleteMapping("/unblock/{userId}")
    @ApiOperation("유저 차단 해제")
    public ApiResponse unblockUser(@UserId Long myId, @PathVariable Long userId) {
        userService.unblockUser(myId, userId);
        return ApiResponse.success(SuccessCode.USER_UNBLOCK_SUCCESS);
    }
}
