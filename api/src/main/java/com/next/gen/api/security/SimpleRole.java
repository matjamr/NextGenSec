package com.next.gen.api.security;

import com.next.gen.sec.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class SimpleRole implements GrantedAuthority {

    private final Role role;

    @Override
    public String getAuthority() {
        return role.getValue();
    }
}
