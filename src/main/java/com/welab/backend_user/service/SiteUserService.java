package com.welab.backend_user.service;

import com.welab.backend_user.domain.dto.SiteUser;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.domain.repository.SiteUserRepository;
// import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // 의존성 추가 <-> Autowired 보다 좋은이유?
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;

    // 회원 가입 기능 추가
    // 원자성 확보를 위한 트랜젝셔널
    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto) {
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);
    }

    // 로그인 확인 기능
    @Transactional
    public boolean loginUser(SiteUserLoginDto siteUserLoginDto) {
        SiteUser siteUser = siteUserLoginDto.toEntity(); // 아이디와 패스워드를 가져오고
        // TODO : JPA 를 이용해서 확인하는 로직 구현
        String userId = siteUser.getUserId();
        String password = siteUser.getPassword();

        boolean output = siteUserRepository.findByUserId(userId)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);

        return output;
    }
}
