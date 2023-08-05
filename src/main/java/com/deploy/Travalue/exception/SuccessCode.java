package com.deploy.Travalue.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    SIGNUP_SUCCESS(HttpStatus.OK, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료되었습니다."),

    // user
    NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 업데이트가 완료되었습니다."),
    CHECK_NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 중복 검사가 완료되었습니다."),
    UPDATE_PROFILE_SUCCESS(HttpStatus.OK, "프로필 업데이트가 완료되었습니다."),
    CREATE_MY_TRIP_SUCCESS(HttpStatus.CREATED, "나의 여행지가 생성되었습니다."),
    GET_MY_TRIP_LIST_SUCCESS(HttpStatus.OK, "나의 여행지 리스트 조회성공"),
    DELETE_MY_TRIP_SUCCESS(HttpStatus.OK, "나의 여행지 삭제 성공"),
    GET_MY_PAGE_SUCCESS(HttpStatus.OK, "마이 페이지 조회성공"),
    USER_BLOCK_SUCCESS(HttpStatus.OK, "유저 차단 성공"),
    USER_UNBLOCK_SUCCESS(HttpStatus.OK, "유저 차단 해제 성공"),
    READ_LIKED_TRAVEL_SUCCESS(HttpStatus.OK, "좋아요한 게시물 전체 조회 성공"),

    // travel
    CREATE_CATEGORY_SUCCESS(HttpStatus.CREATED, "카테고리가 생성됐습니다."),
    UPDATE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 업데이트에 성공했습니다."),
    DELETE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리가 삭제됐습니다."),
    GET_CATEGORT_LIST_SUCCESS(HttpStatus.OK, "카테고리 조회가 완료됐습니다."),

    CREATE_TRAVELLER_SUCCESS(HttpStatus.CREATED, "트레블러 생성 성공"),
    UPDATE_TRAVELLER_SUCCESS(HttpStatus.OK, "트레블러 업데이트 성공"),
    READ_TRAILERS_SUCCESS(HttpStatus.OK, "트레일러 전체 조회 성공"),
    READ_TRAVELLERS_SUCCESS(HttpStatus.OK, "트레블러 전체 조회 성공"),

    READ_SHARED_TRAVELLERS_SUCCESS(HttpStatus.OK, "공유 중인 트레블러 전체 조회 성공"),
    READ_SHARED_TRAVELLERS_BY_CATEGORY_SUCCESS(HttpStatus.OK, "공유 중인 트레블러 카테고리별 조회 성공"),

    READ_TRAVEL_SUCCESS(HttpStatus.OK, "트레블러/트레일러 상세 조회 성공"),

    DELETE_TRAVEL_SUCCESS(HttpStatus.OK, "트레블 삭제 성공"),

    READ_SEARCHED_TRAVELLERS_SUCCESS(HttpStatus.OK, "트레블러 검색 성공"),

    READ_HOT_TRAVELLERS_SUCCESS(HttpStatus.OK, "지금 핫한 트레블러 조회 성공"),

    LIKE_TRAVEL_SUCCESS(HttpStatus.CREATED, "게시물 좋아요 성공"),
    UNLIKE_TRAVEL_SUCCESS(HttpStatus.OK, "게시물 좋아요 취소 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
