package com.sec.gen.next.backend.internal.api.internal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RegisterSource {
    GOOGLE("GOOGLE"),
    JWT("JWT");

    final String name;
}
