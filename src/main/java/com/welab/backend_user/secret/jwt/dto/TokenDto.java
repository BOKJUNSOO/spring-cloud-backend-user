package com.welab.backend_user.secret.jwt.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDto {
    @Getter
    @Setter
    @NoArgsConstructor // 따로따로 셋터로 설정 가능하도록 !
    @AllArgsConstructor // 한번에 모두 받는것도
    public static class JwtToken {
        private String token;
        private Integer expiresIn;
    }

    @Getter
    @RequiredArgsConstructor // 필수적으로 모두 있어야함 인스턴스 생성시에
    public static class AccessToken {
        private final JwtToken access;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor //
    public static class AccessRefreshToken {
        private final JwtToken access;
        private final JwtToken refresh;
    }
}
