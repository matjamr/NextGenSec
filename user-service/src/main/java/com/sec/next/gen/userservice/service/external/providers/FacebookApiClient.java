package com.sec.next.gen.userservice.service.external.providers;

import com.next.gen.sec.model.FacebookUserResponse;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facebookAccessTokenClient", url = "https://graph.facebook.com/v19.0")
public interface FacebookApiClient {

    @GetMapping("me")
    FacebookUserResponse getUserInfo(@RequestParam("access_token") String accessToken, @RequestParam String fields);
}

