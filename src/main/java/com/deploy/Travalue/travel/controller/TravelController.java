package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.TravellerRequestDto;
import com.deploy.Travalue.travel.domain.TravelContentInfoVO;
import com.deploy.Travalue.travel.service.TravelService;
import com.deploy.Travalue.travel.service.dto.response.TrailersResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravelResponseDto;
import com.deploy.Travalue.travel.service.dto.response.TravellersResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

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
        return ApiResponse.success(SuccessCode.CREATE_CATEGORY_SUCCESS);
    }

    @ApiOperation("Trailer 전체 조회 API")
    @GetMapping("/trailer")
    public ApiResponse<List<TrailersResponseDto>> getTrailers() {
        final List<TrailersResponseDto> trailers = travelService.getTrailers();
        return ApiResponse.success(SuccessCode.READ_TRAILERS_SUCCESS, trailers);
    }

    @ApiOperation("Traveller 전체 조회 API")
    @GetMapping("/traveller")
    public ApiResponse<List<TravellersResponseDto>> getTravellers() {
        final List<TravellersResponseDto> travellers = travelService.getTravellers();
        return ApiResponse.success(SuccessCode.READ_TRAVELLERS_SUCCESS, travellers);
    }

    @ApiOperation("Traveller 상세 조회 API")
    @GetMapping("/{id}")
    public ApiResponse<TravelResponseDto> getTravellerById(@PathVariable("id") Long travelId) {
        final TravelResponseDto data = travelService.getTravellerById(travelId);
        return ApiResponse.success(SuccessCode.READ_TRAVEL_SUCCESS, data);
    }
}
