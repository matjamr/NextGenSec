package com.next.gen.api.security;

import com.next.gen.sec.model.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserServiceClient userServiceClient;
    private final CustomAuthenticationMapper authenticationMapper;
    private final SecurityPropertiesConfig securityPropertiesConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (isPermitAllEndpoint(request.getRequestURI(), request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = request.getHeader("Authorization");

            if (isNull(token)) {
                throw new ServiceException("Token is missing");
            }

            Authentication authentication = getAuthenticationFromToken(token, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ServiceException e) {
            log.error("Service exception: {}", e.getMessage());
            returnError(response, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Internal server error", e);
            returnError(response, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthenticationFromToken(String token, HttpServletRequest request) throws ServiceException {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> headers.put(headerName, request.getHeader(headerName)));

        UserModel authorizedUser = userServiceClient.getAccessToken(token, headers);
        CustomAuthentication customAuthentication = authenticationMapper.map(authorizedUser);
        return customAuthenticationManager.authenticate(customAuthentication);
    }

    private void returnError(HttpServletResponse response, String m) throws IOException {
        response.resetBuffer();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().print(Map.of("message", m).toString());
        response.flushBuffer();
    }

    private boolean isPermitAllEndpoint(String requestURI, String method) {
        return securityPropertiesConfig.getPaths().stream()
                .anyMatch(pair -> requestURI.matches(pair.getUrl()) &&
                        pair.getMethod().equals(method) &&
                        pair.getAccess().equals("permitAll"));
    }
}
