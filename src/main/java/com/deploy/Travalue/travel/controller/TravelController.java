package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.TravellerRequestDto;
import com.deploy.Travalue.travel.controller.dto.response.LikeCountResponseDto;
import com.deploy.Travalue.travel.domain.TravelContentInfoVO;
import com.deploy.Travalue.travel.service.TravelService;
import com.deploy.Travalue.travel.service.dto.response.HotTravellersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravelResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravellersResponseDto;
import com.deploy.Travalue.user.controller.dto.SharedTravelDetailDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class TravelController {

    private final S3Service s3Service;
    private final TravelService travelService;

    @Auth
    @ApiOperation("Traveller 생성 API")
    @PostMapping("traveller")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(@UserId Long userId, @ModelAttribute @Valid final TravellerRequestDto request) {
        final String thumbnailImagePath = s3Service.uploadImage(request.getThumbnail(), "traveller");
        final List<MultipartFile> travellerContentImageList = new ArrayList<>();
        for (TravelContentInfoVO travelContent : request.getTravelContentInfoList()) {
            travellerContentImageList.add(travelContent.getImage());
        }
        final List<String> travellerContentImageUrlList = s3Service.uploadImages(travellerContentImageList, "traveller");

        travelService.create(userId, thumbnailImagePath, travellerContentImageUrlList, request);
        return ApiResponse.success(SuccessCode.CREATE_TRAVELLER_SUCCESS);
    }

    @Auth
    @ApiOperation("Travveller 수정 API")
    @PutMapping("traveller/{travellerId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(@UserId Long userId, @PathVariable Long travellerId, @ModelAttribute @Valid final TravellerRequestDto request) {
        final String thumbnailImageUrl = s3Service.uploadImage(request.getThumbnail(), "traveller");
        final List<MultipartFile> travellerContentImageList = new ArrayList<>();
        for (TravelContentInfoVO travelContent : request.getTravelContentInfoList()) {
            travellerContentImageList.add(travelContent.getImage());
        }
        final List<String> travellerContentImageUrlList = s3Service.uploadImages(travellerContentImageList, "traveller");

        travelService.update(userId, travellerId, thumbnailImageUrl, travellerContentImageUrlList, request);
        return ApiResponse.success(SuccessCode.UPDATE_TRAVELLER_SUCCESS);
    }

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("trailer")
    public ApiResponse<List<TrailersResponseDto>> getTrailers() {
        final List<TrailersResponseDto> trailers = travelService.getTrailers();
        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailers);
    }

    @ApiOperation("Traveller 전체 조회 API")
    @GetMapping("traveller")
    public ApiResponse<List<TravellersResponseDto>> getTravellers() {
        final List<TravellersResponseDto> travellers = travelService.getTravellers();
        return ApiResponse.success(SuccessCode.READ_TRAVELLERS_SUCCESS, travellers);
    }

    @Auth
    @ApiOperation("Travel 상세 조회 API")
    @GetMapping("{id}")
    public ApiResponse<TravelResponseDto> getTravelById(@PathVariable("id") Long travelId, @UserId Long userId) {
        final TravelResponseDto data = travelService.getTravelById(travelId, userId);
        return ApiResponse.success(SuccessCode.READ_TRAVEL_SUCCESS, data);
    }

    @ApiOperation("Traveller 삭제 API")
    @DeleteMapping("traveller/{id}")
    public ApiResponse deleteTravelById(@PathVariable("id") Long travelId) {
        travelService.deleteTravelById(travelId);
        return ApiResponse.success(SuccessCode.DELETE_TRAVEL_SUCCESS);
    }

    @Auth
    @ApiOperation("공유 중인 Traveller 전체 조회 API")
    @GetMapping("traveller/share/{pageOwnerUserId}")
    public ApiResponse<?> getTravellers(@PathVariable Long pageOwnerUserId, @UserId Long userId) {
        log.info("공유 중인 Travellser 전체 조회 API - pageOwnerUserId : " + pageOwnerUserId);
        final List<SharedTravelDetailDto> travellers = travelService.getTravellersByProfileOwnerId(userId, pageOwnerUserId);
        return ApiResponse.success(SuccessCode.READ_SHARED_TRAVELLERS_SUCCESS, travellers);
    }

    @Auth
    @ApiOperation("공유 중인 Traveller 카테고리별 조회 API")
    @GetMapping("traveller/share/{pageOwnerUserId}/{categoryId}")
    public ApiResponse<?> getTravellers(@PathVariable Long pageOwnerUserId, @PathVariable Long categoryId, @UserId Long userId) {
        // TODO: 이거 getRravellers 함수명 3곳에서 공유했는데 변경해주는게 좋겠지? 오버로딩으로 되길래 사용했는데...
        log.info("공유 중인 Travellser 카테고리별 조회 API - pageOwnerUserId : " + pageOwnerUserId + " categoryId : " + categoryId + " userId : " + userId);

        final List<SharedTravelDetailDto> travellers = travelService.getTravellersByCategory(userId, pageOwnerUserId, categoryId);

        return ApiResponse.success(SuccessCode.READ_SHARED_TRAVELLERS_BY_CATEGORY_SUCCESS, travellers);
    }

    @ApiOperation("Traveller 검색 API")
    @GetMapping("traveller/search")
    public ApiResponse<List<TravellersResponseDto>> getSearchedTravellers(@RequestParam String keyword) {
        final List<TravellersResponseDto> data = travelService.getSearchedTravellers(keyword);
        return ApiResponse.success(SuccessCode.READ_SEARCHED_TRAVELLERS_SUCCESS, data);
    }

    @ApiOperation("지금 핫한 Traveller 조회 API")
    @GetMapping("traveller/hot")
    public ApiResponse<List<HotTravellersResponseDto>> getHotTravellers() {
        final List<HotTravellersResponseDto> data = travelService.getHotTravellers();
        return ApiResponse.success(SuccessCode.READ_HOT_TRAVELLERS_SUCCESS, data);
    }

    @Auth
    @ApiOperation("게시물 좋아요 API")
    @PostMapping("/{postId}/like")
    public ApiResponse<LikeCountResponseDto> travelLike(@UserId Long userId, @PathVariable Long postId) {
        return ApiResponse.success(SuccessCode.LIKE_TRAVEL_SUCCESS, travelService.travelLike(userId, postId));
    }

    @Auth
    @ApiOperation("게시물 좋아요 취소 API")
    @DeleteMapping("{postId}/unlike")
    public ApiResponse<LikeCountResponseDto> travelUnlike(@UserId Long userId, @PathVariable Long postId) {
        return ApiResponse.success(SuccessCode.UNLIKE_TRAVEL_SUCCESS, travelService.travelUnlike(userId, postId));
    }
}
