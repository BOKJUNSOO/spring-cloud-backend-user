package com.welab.backend_user.service;

import com.welab.backend_user.common.exception.BadParameter;
import com.welab.backend_user.common.exception.NotFound;
import com.welab.backend_user.domain.dto.SiteUser;
import com.welab.backend_user.domain.dto.SiteUserLoginDto;
import com.welab.backend_user.domain.dto.SiteUserRefreshDto;
import com.welab.backend_user.domain.dto.SiteUserRegisterDto;
import com.welab.backend_user.domain.repository.SiteUserRepository;
// import jakarta.transaction.Transactional;
import com.welab.backend_user.remote.alim.RemoteAlimService;
import com.welab.backend_user.secret.hash.SecureHashUtils;
import com.welab.backend_user.secret.jwt.dto.TokenDto;
import com.welab.backend_user.secret.jwt.props.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // 의존성 추가 <-> Autowired 보다 좋은이유?
public class SiteUserService {
    // 의존성 주입
    private final SiteUserRepository siteUserRepository;
    private final TokenGenerator tokenGenerator;
    private final RemoteAlimService remoteAlimService;
    // 회원 가입 기능 추가
    // 원자성 확보를 위한 트랜젝셔널
    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto) {
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);
        // ** 생각해볼 부분 : Transactional 로 묶여 있기 때문에 만약 alim service 를 실패하면 회원가입이 안됨
        // 이를 메세지 발행으로 커버할 수 있다 ! -> alim service (remote) 삭제가능
        // remoteAlimService.hello();
    }

    // 로그인 확인 기능
//    @Transactional
//    public boolean loginUser(SiteUserLoginDto siteUserLoginDto) {
//        SiteUser siteUser = siteUserLoginDto.toEntity(); // 아이디와 패스워드를 가져오고
//        // TODO : JPA 를 이용해서 확인하는 로직 구현
//        String userId = siteUser.getUserId();
//        String password = siteUser.getPassword();
//
//        boolean output = siteUserRepository.findByUserId(userId)
//                .map(user -> user.getPassword().equals(password))
//                .orElse(false);
//
//        return output;
//    }


    // 로그인에 성공하면 Token 을 발행해주는 서비스 로직
    // DB 에 로그저장이 필요하다면 readOnly 옵션 삭제
    @Transactional(readOnly = true)
    public TokenDto.AccessRefreshToken login(SiteUserLoginDto siteUserLoginDto) {
        /* 로그인을 성공한 유저에 대해서 토큰을 발행해주는 함수*/
        SiteUser user = siteUserRepository.findByUserId(siteUserLoginDto.getUserId());
        // 존재하지 않는 아이디라면
        if (user == null) {
            throw new NotFound("아이디 또는 비밀번호를 확인해주세요.");
        }

        // 사용자 입력 DTO 와 비교한 비밀번호가 다르다면
        if (!SecureHashUtils.matches(siteUserLoginDto.getPassword(), user.getPassword())) {
            throw new BadParameter("아이디 또는 비밀번호를 확인해주세요.");
        }
        
        // TODO : Gateway 헤더에서 파싱해오는 로직 필요
        return tokenGenerator.generateAccessRefreshToken(siteUserLoginDto.getUserId(), "WEB");
    }

    @Transactional(readOnly = true)
    public TokenDto.AccessToken refresh(SiteUserRefreshDto refreshDto) {
        /* */
        String userId = tokenGenerator.validateJwtToken(refreshDto.getToken());
        if (userId == null) {
            throw new BadParameter("토큰이 유효하지 않습니다.");
        }
        SiteUser user = siteUserRepository.findByUserId(userId);
        if(user ==null) {
            throw new NotFound("사용자를 찾을 수 없습니다.");
        }
        return tokenGenerator.generateAccessToken(userId,"WEB");
    }
}
