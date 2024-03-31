package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.service.authorization.AuthorizationService;
import com.sec.next.gen.userservice.service.authorization.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class AuthorizationController {

    private final Function<RegistrationSource, AuthorizationService> sourceAuthorizationServiceDispatcher;
    private final JwtService jwtService;

    @PostMapping("/verify")
    public GoogleAuthorizedUser getUserInfo(@RequestHeader String token, @RequestHeader RegistrationSource source) {
        return sourceAuthorizationServiceDispatcher.apply(source)
                .getUserInfo(token, source);
    }

    @PostMapping("/token")
    public String createToken(@RequestBody GoogleAuthorizedUser authorizedUser) {
        return jwtService.generateToken(authorizedUser);
    }
}
