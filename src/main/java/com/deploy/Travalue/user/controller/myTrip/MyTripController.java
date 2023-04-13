package com.deploy.Travalue.user.controller.myTrip;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.myTrip.request.MyTripRequestDto;
import com.deploy.Travalue.user.service.myTrip.MyTripService;
import com.deploy.Travalue.user.service.myTrip.dto.response.MyTripResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyTripController {
    private final MyTripService myTripService;

    @Auth
    @PostMapping("/profile/travel")
    public ApiResponse createMyTrip(@UserId Long userId, @RequestBody @Valid final MyTripRequestDto request) {
        myTripService.createMyTrip(userId, request);
        return ApiResponse.success(SuccessCode.CREATE_MY_TRIP_SUCCESS);
    }

    @Auth
    @GetMapping("/profile/travel")
    public ApiResponse<List<MyTripResponseDto>> getMyTripList(@UserId Long userId) {
        return ApiResponse.success(SuccessCode.GET_MY_TRIP_LIST_SUCCESS, myTripService.getMyTripList(userId));
    }
}
