package com.sec.next.gen.userservice.controller;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.Source;
import com.sec.next.gen.userservice.service.AuthorizationService;
import com.sec.next.gen.userservice.service.GoogleUserInfoService;
import com.sec.next.gen.userservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class AuthorizationController {

    private final Function<Source, AuthorizationService> sourceAuthorizationServiceDispatcher;
    private final JwtService jwtService;

    @PostMapping("/verify")
    public Mono<AuthorizedUser> getUserInfo(@RequestHeader String token, @RequestHeader Source source) {
        return sourceAuthorizationServiceDispatcher.apply(source)
                .getUserInfo(token, source);
    }

    @PostMapping("/token")
    public String createToken(@RequestBody AuthorizedUser authorizedUser) {
        return jwtService.generateToken(authorizedUser);
    }
}
