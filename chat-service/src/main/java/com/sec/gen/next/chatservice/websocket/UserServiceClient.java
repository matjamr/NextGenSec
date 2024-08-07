package com.sec.gen.next.chatservice.websocket;

import com.next.gen.sec.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userServiceClient", url = "${services.user-service.url}")
public interface UserServiceClient {

    @PostMapping("/user/security/verify")
    UserModel getAccessToken(@RequestHeader("Authorization") String token);

}
