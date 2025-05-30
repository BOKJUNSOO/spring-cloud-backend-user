package com.welab.backend_user.api.open;

import com.welab.backend_user.common.dto.ApiResponseDto;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRefreshDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.remote.alim.RemoteAlimService;
import com.welab.backend_user.secret.jwt.dto.TokenDto;
import com.welab.backend_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
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
public class UserAuthController {
    private final SiteUserService siteUserService;

    @PostMapping(value="/register")
    public ApiResponseDto<String> sms(@RequestBody @Valid SiteUserRegisterDto siteUserRegisterDto) {
        siteUserService.registerUser(siteUserRegisterDto);
        return ApiResponseDto.defaultOk();
    }

    // JWT 토큰을 리턴해주어야함
    @PostMapping(value="/login")
    public ApiResponseDto<TokenDto.AccessRefreshToken> login(@RequestBody @Valid SiteUserLoginDto siteUserLoginDto) {
        TokenDto.AccessRefreshToken token = siteUserService.login(siteUserLoginDto);
        return ApiResponseDto.createOk(token);
    }

    // refresh 토큰 확인
    @PostMapping(value="/refresh")
    public ApiResponseDto<TokenDto.AccessToken> refresh(@RequestBody @Valid SiteUserRefreshDto siteUserRefreshDto) {
        // TokenDto.AccessToken 타입 추론이 가능하므로 var 로 작성
        var token = siteUserService.refresh(siteUserRefreshDto);
        return ApiResponseDto.createOk(token);
    }
}
