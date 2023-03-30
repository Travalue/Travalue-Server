package com.deploy.Travalue.user.controller.myTrip;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.user.controller.dto.AddTripRequestDto;
import com.deploy.Travalue.user.service.myTrip.MyTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MyTripController {
    private final MyTripService myTripService;

    @PostMapping("/profile/travel")
    public ApiResponse createMyTrip(@RequestBody @Valid final AddTripRequestDto addTripRequestDto) {
        myTripService.createMyTrip(1L, addTripRequestDto);
        return ApiResponse.success(SuccessCode.CREATE_MY_TRIP_SUCCESS);
    }
}
