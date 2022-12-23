package com.deploy.Travalue.controller.test;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    @ApiOperation("[테스트] 서버 확인용 테스트 API")
    @GetMapping("/test")
    public String test() {
        return "서버 테스트 완료";
    }
}
