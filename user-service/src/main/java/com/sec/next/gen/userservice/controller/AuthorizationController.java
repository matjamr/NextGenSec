package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class AuthorizationController {
    private final Function<TokenContext, String> tokenGenerator;
    private final Function<String, UserModel> fromTokenUserProvider;

    @PostMapping("/verify")
    public UserModel getUserInfo(@RequestHeader String token) {
        return fromTokenUserProvider.apply(token);
    }

    @PostMapping("/token")
    public Map<String, String> createToken(@RequestBody(required = false) GoogleAuthorizedUser authorizedUser,
                              @RequestHeader("source") RegistrationSource source,
                              @RequestHeader(value = "token", required = false) String token) {
        return Map.of("token", tokenGenerator.apply(new TokenContext()
                .setAuthorizedUser(authorizedUser)
                .setSource(source)
                .setToken(token)));
    }
}
