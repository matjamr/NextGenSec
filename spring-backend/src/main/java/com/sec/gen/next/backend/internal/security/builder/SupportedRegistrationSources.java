package com.sec.gen.next.backend.internal.security.builder;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class SupportedRegistrationSources implements Predicate<String> {

    private final List<String> supportedRegistrationSources;

    @Override
    public boolean test(String source) {
        return supportedRegistrationSources.stream()
                .anyMatch(supportedRegistrationSources::contains);
    }
}
