package com.welab.backend_user.remote.alim;


import com.welab.backend_user.remote.alim.dto.AlimSendSmsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// JPA 인터페이스와 비슷..
// k8s 에 가서도 feignClient 는 사용함 !!!
@FeignClient(name = "backend-alim" , path = "/backend/alim/v1")
public interface RemoteAlimService {
    @GetMapping(value= "/hello")
    public String hello();

    @PostMapping(value ="/sms")
    public AlimSendSmsDto.Response sendSms(@RequestBody AlimSendSmsDto.Request request);
}

// 비교해볼 원래 코드 !

//@Service
//@RequiredArgsConstructor
//public class RemoteAlimService {
//    private final RestTemplate restTemplate;
//    public String hello() {
//        return restTemplate.getForObject(
//                "http://backend-alim/backend/alim/v1/hello", String.class
//        );
//    }
//    public SendSmsDto.Response sendSms(SendSmsDto.Request request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<SendSmsDto.Request> httpRequest = new HttpEntity<>(request, headers);
//        ResponseEntity<SendSmsDto.Response> httpResponse = restTemplate.exchange(
//                "http://backend-alim/backend/alim/v1/sms", HttpMethod.POST, httpRequest, SendSmsDto.Response.class
//        );
//        return httpResponse.getBody();
//    }
//}