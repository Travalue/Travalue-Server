package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.travel.service.TravelService;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TravelController {
    private final TravelService trailerService;

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("/post/trailers")
    public void getTrailers() {
//        final List<TrailersResponseDto> trailers = trailerService.getTrailers();
//        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailers);
    }
}
