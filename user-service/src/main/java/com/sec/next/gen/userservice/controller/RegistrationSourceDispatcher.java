package com.sec.next.gen.userservice.controller;

import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.service.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class RegistrationSourceDispatcher implements Function<RegistrationSource, AuthorizationService> {

    private final Map<RegistrationSource, AuthorizationService> authorizationServiceMap;

    @Override
    public AuthorizationService apply(RegistrationSource source) {
        return Optional.ofNullable(authorizationServiceMap.get(source))
                .orElseThrow(() -> new RuntimeException("Invalid source"));
    }
}
