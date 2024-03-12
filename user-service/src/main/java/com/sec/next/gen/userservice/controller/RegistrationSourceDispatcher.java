package com.sec.next.gen.userservice.controller;

import com.sec.next.gen.userservice.models.Source;
import com.sec.next.gen.userservice.service.AuthorizationService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class RegistrationSourceDispatcher implements Function<Source, AuthorizationService> {

    private final Map<Source, AuthorizationService> authorizationServiceMap;

    @Override
    public AuthorizationService apply(Source source) {
        return Optional.ofNullable(authorizationServiceMap.get(source))
                .orElseThrow(() -> new RuntimeException("Invalid source"));
    }
}
