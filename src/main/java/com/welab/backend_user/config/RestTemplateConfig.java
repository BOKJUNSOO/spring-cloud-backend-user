package com.welab.backend_user.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced; // 클라이언트에 정의되어 있음
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 내가 만든 클래스를 bean에 등록
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() { // 사용하지만 내가 만든 클래스는 아님 -> bean
        return new RestTemplate();
    }
}
