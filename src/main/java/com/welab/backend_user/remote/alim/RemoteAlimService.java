package com.welab.backend_user.remote.alim;

import com.welab.backend_user.config.RestTemplateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service // 컴포넌트(사용자 정의 클래스) 등록 의존성 주입
@RequiredArgsConstructor // 생성자 의존성 주입
public class RemoteAlimService {
    // Autowired 도 가능하지만 RAC 를 더 많이 씀
    private final RestTemplate restTemplate;
//
//    public RemoteAlimService(RestTemplate restTemplate) { // RAC 없이 쓸때
//        this.restTemplate = restTemplate;
//    }
    public String hello() {

        return restTemplate.getForObject(
                "http://alim-service/backend/alim/v1/hello",
                String.class
        );
    }
}
