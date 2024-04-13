package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class AuthorizationController {

//    private final Function<String, AuthorizationService> sourceAuthorizationServiceDispatcher;
    private final Function<TokenContext, String> tokenGenerator;

    @PostMapping("/verify")
    public GoogleAuthorizedUser getUserInfo(@RequestHeader String token) {
//        return sourceAuthorizationServiceDispatcher.apply(token)
//                .getUserInfo(token);
        return null;
    }

    @PostMapping("/token")
    public String createToken(@RequestBody(required = false) GoogleAuthorizedUser authorizedUser,
                              @RequestHeader("source") RegistrationSource source,
                              @RequestHeader(value = "token", required = false) String token) {
        return tokenGenerator.apply(new TokenContext()
                .setAuthorizedUser(authorizedUser)
                .setSource(source)
                .setToken(token));
    }
}
