package com.sec.next.gen.userservice.service.internal.authorization.token.generator;

import com.sec.next.gen.userservice.service.internal.authorization.token.TokenParams;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class AccessTokenCreator implements TokenCreator {

    private final Supplier<Key> keyProvider;

    @Override
    public String createToken(TokenParams tokenParams) {
        return Jwts.builder()
                .setClaims(Map.of())
                .setSubject(tokenParams.googleAuthorizedUser().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenParams.msDuration()))
                .signWith(keyProvider.get(), SignatureAlgorithm.HS256)
                .compact();
    }
}
