package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.Token;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class AuthorizationController {
    private final Function<TokenContext, Token> tokenGenerator;
    private final Function<TokenContext, Token> refreshTokenBasedGenerator;
    private final Function<String, UserModel> fromTokenUserProvider;

    @PostMapping("/verify")
    public UserModel getUserInfo(@RequestHeader(name = "Authorization") String token) {
        return fromTokenUserProvider.apply(token);
    }

    @PostMapping("/token")
    public ResponseEntity<Token> createToken(@RequestBody(required = false) GoogleAuthorizedUser authorizedUser,
                             @RequestHeader("source") RegistrationSource source,
                             @RequestHeader(value = "token", required = false) String token) {

        var responseToken = tokenGenerator.apply(new TokenContext()
                .setAuthorizedUser(authorizedUser)
                .setSource(source)
                .setToken(token));

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", responseToken.getAccessToken());
        headers.add("refresh_token", responseToken.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Token> refreshToken(@RequestHeader(value = "Authorization") String token) {
        var newToken = refreshTokenBasedGenerator.apply(new TokenContext().setToken(token));

        return ResponseEntity.ok()
                .header("access_token", newToken.getAccessToken())
                .header("refresh_token", token)
                .body(newToken);
    }
}
