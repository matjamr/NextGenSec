package com.sec.next.gen.userservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceError extends RuntimeException {
    private final Error error;
}
