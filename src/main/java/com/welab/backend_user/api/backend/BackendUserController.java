package com.welab.backend_user.api.backend;


import com.welab.backend_user.domain.dto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/backend/user/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackendUserController {
    @GetMapping(value="/hello")
    public String hello() {
        return "유저 백엔드 서비스가 호출되었습니다";
    }

    @PostMapping(value ="/info")
    public UserInfoDto.Response info(@RequestBody UserInfoDto.Request request) {
        log.info("request userId ={}",request.getUserId());

        UserInfoDto.Response response = new UserInfoDto.Response();
        // 타입 추론이 가능한 경우에 `var` 키워드를 쓰기도함 (추가로 중복을 피하기 위함)
        // var response = new UserInfoDto.Response();

        // System.out.println(request.getUserId());
        response.setUserId(request.getUserId());
        // dummy data
        response.setPhoneNumber("010-3345-7633");
        response.setUserName("복준수");

        return response;
    }

    @GetMapping(value = "/getinfo")
    public UserInfoDto.Response getInfo(@RequestParam("userId") String request) {
        UserInfoDto.Response response = new UserInfoDto.Response();
        response.setUserId(request);
        response.setPhoneNumber("010");
        response.setUserName("hi");

        return response;
    }
}
