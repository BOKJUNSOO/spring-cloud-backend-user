package com.welab.backend_user.secret.jwt.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // 환경변수 참조를 위한 Bean 등록
@ConfigurationProperties(value = "jwt", ignoreUnknownFields = true) // yaml 파일의 `jwt` 환경변수를 사용
@Getter
@Setter
public class JwtConfigProperties {
    // 환경변수의 값들을 camel case 로 읽어오는게 가능하다.
    // format 변환도 자동으로 가능
    private Integer expiresIn;
    private Integer mobileExpiresIn;
    private Integer tabletExpiresIn;
    private String secretKey;
}