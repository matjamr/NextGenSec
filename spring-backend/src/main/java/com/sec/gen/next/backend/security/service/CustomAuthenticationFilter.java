package com.sec.gen.next.backend.security.service;

import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.internal.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String source = request.getHeader("source");
        String token = request.getHeader("token");
        Authentication authenticatedObject;

        if(isNull(source) || isNull(token)) {
            throw new RuntimeException("Invalid token");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("source", source);
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        AuthorizedUser authorizedUser = restTemplate.exchange("http://localhost:8081/api/security/verify", HttpMethod.POST, entity, AuthorizedUser.class)
                .getBody();

        System.out.println(authorizedUser);
        CustomAuthentication customAuthentication = new CustomAuthentication(authorizedUser);
        authenticatedObject = customAuthenticationManager.authenticate(customAuthentication);


        SecurityContextHolder.getContext().setAuthentication(authenticatedObject);
        filterChain.doFilter(request, response);
    }
}
