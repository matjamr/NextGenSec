package com.sec.next.gen.userservice.models;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Source {
    GOOGLE("GOOGLE"),
    FACEBOOK("FACEBOOK"),
    LINKEDIN("LINKEDIN"),
    JWT("JWT");

    private final String value;
}
