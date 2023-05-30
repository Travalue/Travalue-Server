package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.travel.service.TravelService;
import com.deploy.Travalue.travel.service.dto.response.HotTravellersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravelResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravellersResponseDto;
import com.deploy.Travalue.user.controller.dto.SharedTravelDetailDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @Auth
    @ApiOperation("Traveller 상세 조회 API")
    @GetMapping("/post/{id}")
    public ApiResponse<TravelResponseDto> getTravellerById(@PathVariable("id") Long travelId, @UserId Long userId) {
        final TravelResponseDto data = travelService.getTravellerById(travelId, userId);
        return ApiResponse.success(SuccessCode.READ_TRAVEL_SUCCESS, data);
    }

    @ApiOperation("Traveller 삭제 API")
    @DeleteMapping("/post/{id}")
    public ApiResponse deleteTravelById(@PathVariable("id") Long travelId) {
        travelService.deleteTravelById(travelId);
        return ApiResponse.success(SuccessCode.DELETE_TRAVEL_SUCCESS);
    }

    @ApiOperation("공유 중인 Traveller 전체 조회 API")
    @GetMapping("/post/traveller/{userId}")
    public ApiResponse<?> getTravellers(@PathVariable Long userId) {
        log.info("공유 중인 Travellser 전체 조회 API - userId : " + userId);
        final List<SharedTravelDetailDto> travellers = travelService.getTravellersByProfileOwnerId(userId);
        return ApiResponse.success(SuccessCode.READ_SHARED_TRAVELLERS_SUCCESS, travellers);
    }

    @ApiOperation("공유 중인 Traveller 카테고리별 조회 API")
    @GetMapping("/post/traveller/{userId}/{categoryId}")
    public ApiResponse<?> getTravellers(@PathVariable Long userId, @PathVariable Long categoryId) {
        // TODO: 이거 getRravellers 함수명 3곳에서 공유했는데 변경해주는게 좋겠지? 오버로딩으로 되길래 사용했는데...
        log.info("공유 중인 Travellser 카테고리별 조회 API - userId : " + userId + " categoryId : " + categoryId);

        final List<SharedTravelDetailDto> travellers = travelService.getTravellersByCategory(userId, categoryId);

        return ApiResponse.success(SuccessCode.READ_SHARED_TRAVELLERS_BY_CATEGORY_SUCCESS, travellers);
    }

    @ApiOperation("Traveller 검색 API")
    @GetMapping("/post/traveller/")
    public ApiResponse<List<TravellersResponseDto>> getSearchedTravellers(@RequestParam String keyword) {
        final List<TravellersResponseDto> data = travelService.getSearchedTravellers(keyword);
        return ApiResponse.success(SuccessCode.READ_SEARCHED_TRAVELLERS_SUCCESS, data);
    }

    @ApiOperation("지금 핫한 Traveller 조회 API")
    @GetMapping("post/traveller/hot")
    public ApiResponse<List<HotTravellersResponseDto>> getHotTravellers() {
        final List<HotTravellersResponseDto> data = travelService.getHotTravellers();
        return ApiResponse.success(SuccessCode.READ_HOT_TRAVELLERS_SUCCESS, data);
    }
}
