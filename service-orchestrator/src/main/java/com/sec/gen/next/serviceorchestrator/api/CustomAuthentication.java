package com.sec.gen.next.serviceorchestrator.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.PlacesModel;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.Role;
import jakarta.ws.rs.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
public class CustomAuthentication implements Authentication {

    private String id;
    private String email;
    private String name;
    private String surname;
    private RegistrationSource source;
    private Role role;
    private List<GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public Object getCredentials() {
        throw new NotSupportedException("Not supported");
    }

    @Override
    public Object getDetails() {
        throw new NotSupportedException("Not supported");
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
