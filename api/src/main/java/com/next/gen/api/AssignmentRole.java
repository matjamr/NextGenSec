package com.next.gen.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssignmentRole {
    USER("user"), ADMIN("admin"), SYSTEM("system");

    private final String roleName;
}
