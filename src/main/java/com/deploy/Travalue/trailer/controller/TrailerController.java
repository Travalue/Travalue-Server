package com.deploy.Travalue.trailer.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.common.exception.SuccessCode;
import com.deploy.Travalue.trailer.service.TrailerService;
import com.deploy.Travalue.trailer.service.dto.response.TrailersResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
public class TrailerController {
    private final TrailerService trailerService;

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("/post/trailers")
    public ApiResponse<List<TrailersResponseDto>> getTrailers() {
        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailerService.getTrailers());
    }
}
