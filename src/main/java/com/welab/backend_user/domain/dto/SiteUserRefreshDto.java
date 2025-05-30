package com.welab.backend_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRefreshDto {
    // pricate String userId; // 있으면 안됨 토큰에서 userId 를 가져올 수 있도록 구현할것
    @NotBlank(message ="리프레시 토큰을 입력하세요.")
    private String token;
}
