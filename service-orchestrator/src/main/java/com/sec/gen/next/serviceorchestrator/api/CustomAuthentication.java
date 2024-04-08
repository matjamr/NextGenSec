package com.sec.gen.next.serviceorchestrator.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@Builder
public class CustomAuthentication implements Authentication {

    private String email;
    private String givenName;
    private String familyName;
    private String picture;
    private RegistrationSource source;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
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
        return email;
    }
}
