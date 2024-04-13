package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.api.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.sec.next.gen.userservice.config.Error.EXPIRED_TOKEN;

@RequiredArgsConstructor
public class TokenDecoder implements Function<String, String> {

    private final Supplier<Key> keySupplier;

    @Override
    public String apply(String token) {
        if(isTokenExpired(token)){
            throw EXPIRED_TOKEN.getError();
        }

        return extractUsername(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(keySupplier.get())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
