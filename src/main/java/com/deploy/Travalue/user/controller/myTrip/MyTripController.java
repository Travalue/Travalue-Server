package com.deploy.Travalue.user.controller.myTrip;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.myTrip.request.MyTripRequestDto;
import com.deploy.Travalue.user.service.myTrip.MyTripService;
import com.deploy.Travalue.user.service.myTrip.dto.response.MyTripResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mytrip")
public class MyTripController {
    private final MyTripService myTripService;

    @Auth
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createMyTrip(@UserId Long userId, @RequestBody @Valid final MyTripRequestDto request) {
        myTripService.createMyTrip(userId, request);
        return ApiResponse.success(SuccessCode.CREATE_MY_TRIP_SUCCESS);
    }
}
