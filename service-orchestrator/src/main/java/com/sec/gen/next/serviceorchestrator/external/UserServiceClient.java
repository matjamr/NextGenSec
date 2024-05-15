package com.sec.gen.next.serviceorchestrator.external;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.external.SimpleErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userServiceClient", url = "${services.user-service.url}", configuration = SimpleErrorDecoder.class)
public interface UserServiceClient {

    @PostMapping("/user/security/verify")
    UserModel getAccessToken(@RequestHeader("token") String token);

    @GetMapping("/user/servicing/{email}")
    UserModel getUserByEmail(@PathVariable String email);
}
