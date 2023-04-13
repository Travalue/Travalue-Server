package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.common.exception.SuccessCode;
import com.deploy.Travalue.travel.service.TrailerService;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TrailerController {
    private final TrailerService trailerService;

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("/post/trailers")
    public ApiResponse<List<TrailersResponseDto>> getTrailers() {
        final List<TrailersResponseDto> trailers = trailerService.getTrailers();
        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailers);
    }
}
