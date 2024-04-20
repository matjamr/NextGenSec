package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.sec.next.gen.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.sec.next.gen.userservice.config.Error.PASSWORD_DO_NOT_MATCH;

@RequiredArgsConstructor
public class TokenBuilder implements Function<GoogleAuthorizedUser, String> {

    private final Supplier<Key> keyProvider;

    @Override
    public String apply(GoogleAuthorizedUser authorizedUser) {
        return Jwts.builder()
                .setClaims(Map.of())
                .setSubject(authorizedUser.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 100))
                .signWith(keyProvider.get(), SignatureAlgorithm.HS256)
                .compact();
    }
}
