package com.sec.gen.next.backend.api.internal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RegisterSource {
    GOOGLE("GOOGLE"),
    JWT("JWT");

    final String name;
}
