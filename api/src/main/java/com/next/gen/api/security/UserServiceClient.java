package com.next.gen.api.security;

import com.next.gen.sec.model.UserModel;
import feign.HeaderMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "userServiceClient", url = "${services.user-service.url}", configuration = SimpleErrorDecoder.class)
public interface UserServiceClient {

    @PostMapping("/user/security/verify")
    UserModel getAccessToken(@RequestHeader("Authorization") String token, @HeaderMap Map<String, String> headers);

    @GetMapping("/user/servicing/{email}")
    UserModel getUserByEmail(@PathVariable("email") String email);
}
