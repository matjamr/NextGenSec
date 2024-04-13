package com.sec.next.gen.userservice.service.external.providers;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "googleClient", url = "${external.google}")
public interface GoogleApiClient {

    @GetMapping()
    GoogleAuthorizedUser getUserInfo(@RequestParam("id_token") String idToken);
}
