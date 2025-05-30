package com.welab.backend_user.domain.repository;

import com.welab.backend_user.domain.dto.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    // 단순 확인 여부에서 토큰발행 로직으로 변경
    // Optional<SiteUser> findByUserId(String userId);
    SiteUser findByUserId(String userId);
}
