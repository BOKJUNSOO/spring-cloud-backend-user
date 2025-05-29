package com.welab.backend_user.api.open;

import com.welab.backend_user.common.dto.ApiResponseDto;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

@Slf4j
@RestController
@RequestMapping(value="/api/user/v1/auth",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
// 해당 컨트롤러는 인증이 되지 않아도 게이트 웨이에서 열어두도록 하기 위해 따로 컨트롤러를 작성한다 (auth)
public class UserAuthController {
    private final SiteUserService siteUserService;

    @PostMapping(value="/register")
    public ApiResponseDto<String> sms(@RequestBody @Valid SiteUserRegisterDto siteUserRegisterDto) {
        siteUserService.registerUser(siteUserRegisterDto);
        return ApiResponseDto.defaultOk();
    }

    // JWT 토큰을 리턴해주어야함
    @PostMapping(value="/login")
    public ApiResponseDto<String> login(@RequestBody @Valid SiteUserLoginDto siteUserLoginDto) {

        boolean success = siteUserService.loginUser(siteUserLoginDto);
        if(success) {
            return ApiResponseDto.defaultOk();
        } else {
            // TODO : error 에 대해 Advice 해주는 예외처리 로직 필요
            return ApiResponseDto.createError("1","비밀번호가 틀렸습니다");
        }
    }
}
