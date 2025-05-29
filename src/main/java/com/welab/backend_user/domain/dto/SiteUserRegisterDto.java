package com.welab.backend_user.domain.dto;

import com.welab.backend_user.domain.repository.SiteUserRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRegisterDto {
    // validation 의존성 추가 , NULL 및 빈 문자열 불가능
    @NotBlank(message = "아이디를 입력하세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력하세요.")
    private String phoneNumber;

    @Min(value=1, message="나이는 1살이상이어야 합니다.")
    @Max(value=200, message="나이는 200살 이하여야 합니다.")
    private Integer age;

    // 해당 메서드 내의 속성들은 직접 테이블에 저장되는 데이터가 아니므로
    // Entity 어노테이션을 쓰지 않는다.
    // 이를 위해 따로 보낼 데이터를 만든다 (회원가입시)
    public SiteUser toEntity() {
        SiteUser siteUser = new SiteUser();

        siteUser.setUserId(this.userId);

        // String hashedPassword = someHashUtill function ~_~
        siteUser.setPassword(this.password);
        siteUser.setPhoneNumber(this.phoneNumber);

        siteUser.setAge(this.age);

        return siteUser;
    }
}
