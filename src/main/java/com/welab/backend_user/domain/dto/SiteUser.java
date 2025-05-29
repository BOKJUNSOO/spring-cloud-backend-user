package com.welab.backend_user.domain.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name ="site_user")
// user 정보를 담는 테이블 DTO
public class SiteUser {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name="user_id", unique = true, nullable = false) // 중복되는 id 입력시 500 오류 (unique = true)
    @Getter
    @Setter
    private String userId;

    @Column(name="password",nullable = false)
    @Getter
    @Setter
    private String password;

    @Column(name="phone_number", nullable = false)
    @Getter
    @Setter
    private String phoneNumber;

    @Column(name="deleted", nullable = false)
    @Getter
    @Setter
    private Boolean deleted = false;

    @Column(name="age", nullable = false)
    @Getter
    @Setter
    private Integer age;
}

