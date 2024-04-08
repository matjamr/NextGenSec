package com.sec.gen.next.serviceorchestrator.security.external;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userServiceClient", url = "${services.user-service.url}")
public interface UserServiceClient {

    @PostMapping("/security/verify")
    GoogleAuthorizedUser getAccessToken(@RequestHeader("source") String source,
                                        @RequestHeader("token") String token);
}
