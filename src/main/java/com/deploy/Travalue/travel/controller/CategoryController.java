package com.deploy.Travalue.travel.controller;

import com.deploy.Travalue.common.dto.ApiResponse;
import com.deploy.Travalue.config.interceptor.Auth;
import com.deploy.Travalue.config.resolver.UserId;
import com.deploy.Travalue.exception.SuccessCode;
import com.deploy.Travalue.external.client.aws.S3Service;
import com.deploy.Travalue.travel.controller.dto.request.CategoryRequestDto;
import com.deploy.Travalue.travel.service.CategoryService;
import com.deploy.Travalue.travel.service.dto.response.CategoryListResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final S3Service s3Service;
    private final CategoryService categoryService;

    @Auth
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse create(@UserId Long userId, @ModelAttribute @Valid final CategoryRequestDto request) {
        final String imagePath = s3Service.uploadImage(request.getThumbnail(), "category");
        categoryService.create(userId, imagePath, request);
        return ApiResponse.success(SuccessCode.CREATE_CATEGORY_SUCCESS);
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "{categoryId}")
    public ApiResponse update(@UserId Long userId, @PathVariable final Long categoryId, @ModelAttribute @Valid final CategoryRequestDto request) {
        final String imagePath = s3Service.uploadImage(request.getThumbnail(), "category");
        categoryService.update(userId, categoryId, imagePath, request);
        return ApiResponse.success(SuccessCode.UPDATE_CATEGORY_SUCCESS);
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "{categoryId}")
    public ApiResponse delete(@UserId Long userId, @PathVariable final Long categoryId) {
        categoryService.delete(userId, categoryId);
        return ApiResponse.success(SuccessCode.DELETE_CATEGORY_SUCCESS);
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    public ApiResponse<List<CategoryListResponseDto>> getList(@UserId Long userId) {
        return ApiResponse.success(SuccessCode.GET_CATEGORT_LIST_SUCCESS, categoryService.getList(userId));
    }
}
