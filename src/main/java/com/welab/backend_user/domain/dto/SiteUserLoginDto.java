package com.welab.backend_user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserLoginDto {

    private String userId;
    private String password;

    public SiteUser toEntity() {
        SiteUser siteUser = new SiteUser();

        siteUser.setUserId(this.userId);
        siteUser.setPassword(this.password); // 해싱된 값

        return siteUser;
    }
}
