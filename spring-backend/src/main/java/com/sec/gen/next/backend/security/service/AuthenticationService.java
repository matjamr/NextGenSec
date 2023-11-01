package com.sec.gen.next.backend.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.gen.next.backend.security.api.exception.ServiceException;
import com.sec.gen.next.backend.security.api.internal.ClaimsUser;
import com.sec.gen.next.backend.security.api.internal.RegisterSource;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.service.user.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;


import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import static com.sec.gen.next.backend.security.api.exception.Error.NO_SOURCE_HEADER_INFO;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements Filter {

    private static final String REGISTRATION_SOURCE_KEY = "Source";
    private final Builder<Jwt, ClaimsUser> claimsUserBuilder;
    private final UserService userService;
    private final Predicate<String> supportedRegistrationSources;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var principal = resolvePrincipal();

        if(isNull(principal) || principal == "anonymousUser") {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            processSecuredEndpoint(servletRequest, servletResponse, filterChain, principal);
        }
    }

    private void processSecuredEndpoint(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, Object principal) throws ServletException, IOException {
        if(!(principal instanceof Jwt)) {
            log.error("Provided object is not Jwt convertable");
            throw new ServletException();
        }

        var user = claimsUserBuilder.apply((Jwt) principal);
        try {
            userService.loginUser(user, resolveRegistrationSource(servletRequest));
            servletRequest.setAttribute("PRINCIPAL", user);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServiceException e) {
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.BAD_REQUEST.value());
            ((HttpServletResponse) servletResponse).setHeader("Content-Type", "application/json");
            servletResponse.getWriter().write(objectMapper.writeValueAsString(e.getError()));
        }
    }

    private RegisterSource resolveRegistrationSource(ServletRequest servletRequest) throws ServletException {
        return Optional.ofNullable(servletRequest)
                .map(servletRequest1 -> (HttpServletRequest) servletRequest1)
                .map(rq -> rq.getHeader(REGISTRATION_SOURCE_KEY))
                .filter(supportedRegistrationSources)
                .map(RegisterSource::valueOf)
                .orElseThrow(() -> new ServiceException(NO_SOURCE_HEADER_INFO));
    }

    private Object resolvePrincipal() throws ServletException {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .orElse(null);
    }
}
