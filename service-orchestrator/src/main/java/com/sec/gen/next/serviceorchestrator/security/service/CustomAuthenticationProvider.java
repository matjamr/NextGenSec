package com.sec.gen.next.serviceorchestrator.security.service;

import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if(appSecretKey.equalsIgnoreCase(headerKey)) {
//            log.info("Request have valid key");
//            return new CustomAuthentication(true, headerKey);
//        }
        return (CustomAuthentication) authentication;
//        throw new BadCredentialsException("Secret key in header did not match Application Secret key...");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
