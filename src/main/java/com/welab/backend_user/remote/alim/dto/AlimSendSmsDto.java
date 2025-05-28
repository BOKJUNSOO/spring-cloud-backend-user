package com.welab.backend_user.remote.alim.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 해당 클래스의 DTO 는 domain, user 서비스에서 만든 데이터가 아님!!!
// 따라서 해당 dto 는 alim 에서 만든 dto 이므로 패키지를 따로관리!!
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlimSendSmsDto {
    @Getter
    @Setter
    public static class Request {
        private String userId;
        private String phoneNumber;
        private String title;
        private String message;
    }

    @Getter
    @Setter
    public static class Response {
        private String result;
    }
}
