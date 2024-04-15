package com.sec.gen.next.serviceorchestrator.security.service;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.security.config.SecurityPropertiesConfig;
import com.sec.gen.next.serviceorchestrator.external.user.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.security.mapper.CustomAuthenticationMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sec.gen.next.serviceorchestrator.exception.Error.INVALID_HEADER;
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
            String token = request.getHeader("token");
            Authentication authenticatedObject;

            String requestURI = request.getRequestURI();
            String method = request.getMethod();

            if (isPermitAllEndpoint(requestURI, method)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (isNull(token)) {
                throw new ServiceException(INVALID_HEADER);
            }

            UserModel authorizedUser = userServiceClient.getAccessToken(token);

            CustomAuthentication customAuthentication = authenticationMapper.map(authorizedUser);
            authenticatedObject = customAuthenticationManager.authenticate(customAuthentication);

            SecurityContextHolder.getContext().setAuthentication(authenticatedObject);

        } catch (ServiceException e) {
            e.printStackTrace();
            response.resetBuffer();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.getOutputStream().print(e.getError().toString());
            response.flushBuffer();
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
            response.resetBuffer();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.getOutputStream().print("{\"message\": \"" + e.getMessage() + "\"}");
            response.flushBuffer();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermitAllEndpoint(String requestURI, String method) {
        return securityPropertiesConfig.getPaths().stream()
                .anyMatch(pair -> requestURI.startsWith(pair.getUrl()) && pair.getMethod().equals(method));
    }
}
