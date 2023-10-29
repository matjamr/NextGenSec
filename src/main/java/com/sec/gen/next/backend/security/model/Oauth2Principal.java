package com.sec.gen.next.backend.security.model;

import com.sec.gen.next.backend.security.api.internal.ClaimsUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

@Builder
@Data
public class Oauth2Principal implements Authentication {
    private ClaimsUser claimsUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return claimsUser.getEmail();
    }

    @Override
    public Object getDetails() {
        return claimsUser;
    }

    @Override
    public Object getPrincipal() {
        return (Principal) () -> null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return null;
    }
}
