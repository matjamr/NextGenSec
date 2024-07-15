package com.sec.next.gen.userservice.service.internal.authorization.token.generator;

import com.sec.next.gen.userservice.service.external.redis.RedisService;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenParams;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

//@Component
@RequiredArgsConstructor
public class RefreshTokenCreator implements TokenCreator {

    private final Supplier<Key> keyProvider;
    private final RedisService redisService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public String createToken(TokenParams tokenParams) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("source", getHeader("source"));
        headers.put("user-agent", getHeader("user-agent"));
        headers.put("origin", getHeader("origin"));
        headers.put("sec-ch-ua-mobile", getHeader("sec-ch-ua-mobile"));

        String refreshToken = Jwts.builder()
                .setHeaderParams(headers)
                .setSubject(tokenParams.googleAuthorizedUser().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenParams.msDuration()))
                .signWith(keyProvider.get(), SignatureAlgorithm.HS256)
                .compact();

        redisService.storeRefreshToken(tokenParams.googleAuthorizedUser().getEmail(), refreshToken);
        return refreshToken;
    }

    private String getHeader(String headerName) {
        return httpServletRequest.getHeader(headerName);
    }
}
