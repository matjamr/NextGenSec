package com.sec.next.gen.userservice.controller;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.Source;
import com.sec.next.gen.userservice.service.GoogleUserInfoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/security")
public class GoogleUserInfoController {

    private final GoogleUserInfoService googleUserInfoService;

    public GoogleUserInfoController(GoogleUserInfoService googleUserInfoService) {
        this.googleUserInfoService = googleUserInfoService;
    }

    @GetMapping("/verify")
    public Mono<AuthorizedUser> getUserInfo(@RequestBody String token, @RequestHeader Source source) {
        return googleUserInfoService.getUserInfo(token, source);
    }
}
