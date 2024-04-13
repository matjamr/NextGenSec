package com.sec.next.gen.userservice.service.internal.authorization.token;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class KeyProvider implements Supplier<Key> {
    private final String secretKey;

    @Override
    public Key get() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
