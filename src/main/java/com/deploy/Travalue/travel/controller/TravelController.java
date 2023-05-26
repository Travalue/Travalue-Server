package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.travel.service.TravelService;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravelResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravellersResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TravelController {
    private final TravelService travelService;

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("/post/trailer")
    public ApiResponse<List<TrailersResponseDto>> getTrailers() {
        final List<TrailersResponseDto> trailers = travelService.getTrailers();
        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailers);
    }

    @ApiOperation("Traveller 전체 조회 API")
    @GetMapping("/post/traveller")
    public ApiResponse<List<TravellersResponseDto>> getTravellers() {
        final List<TravellersResponseDto> travellers = travelService.getTravellers();
        return ApiResponse.success(SuccessCode.READ_TRAVELLERS_SUCCESS, travellers);
    }

    @ApiOperation("Traveller 상세 조회 API")
    @GetMapping("/post/{id}")
    public ApiResponse<TravelResponseDto> getTravellerById(@PathVariable("id") Long travelId) {
        final TravelResponseDto data = travelService.getTravellerById(travelId);
        return ApiResponse.success(SuccessCode.READ_TRAVEL_SUCCESS, data);
    }

    @ApiOperation("Traveller 삭제 API")
    @DeleteMapping("/post/{id}")
    public ApiResponse deleteTravelById(@PathVariable("id") Long travelId) {
        travelService.deleteTravelById(travelId);
        return ApiResponse.success(SuccessCode.DELETE_TRAVEL_SUCCESS);
    }
}
