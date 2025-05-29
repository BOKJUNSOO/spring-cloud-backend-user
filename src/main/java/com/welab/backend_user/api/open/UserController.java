package com.welab.backend_user.api.open;

import com.welab.backend_user.common.dto.ApiResponseDto;
import com.welab.backend_user.remote.alim.RemoteAlimService;
import com.welab.backend_user.remote.alim.dto.AlimSendSmsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// 프론트엔드에서 호출하는 컨트롤러 이므로 게이트웨이 외부에 이를 공개함 (사용자 api)
// api 로 시작하는 엔드포인트로 프론트에서 라우팅 함!
@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final RemoteAlimService remoteAlimService;

    @GetMapping(value= "/test")
    public ApiResponseDto<String> test() {
        String response = remoteAlimService.hello();
        return ApiResponseDto.createOk(response);
    }

    @PostMapping(value ="/sms")
    public ApiResponseDto<AlimSendSmsDto.Response> sms(@RequestBody AlimSendSmsDto.Request request) {
        var response = remoteAlimService.sendSms(request);
        return ApiResponseDto.createOk(response);
    }
}
