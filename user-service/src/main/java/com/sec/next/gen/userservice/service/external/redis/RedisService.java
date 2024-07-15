package com.sec.next.gen.userservice.service.external.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long REFRESH_TOKEN_EXPIRATION = 30L;

    public void storeRefreshToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token, REFRESH_TOKEN_EXPIRATION, TimeUnit.DAYS);
    }

    public String getRefreshToken(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
