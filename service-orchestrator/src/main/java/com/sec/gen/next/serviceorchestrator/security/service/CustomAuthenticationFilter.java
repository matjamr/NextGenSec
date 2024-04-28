package com.sec.gen.next.serviceorchestrator.security.service;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.security.config.SecurityPropertiesConfig;
import com.sec.gen.next.serviceorchestrator.external.user.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.security.config.WrappedHttpServletResponse;
import com.sec.gen.next.serviceorchestrator.security.mapper.CustomAuthenticationMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
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
import java.nio.charset.StandardCharsets;

import static com.next.gen.api.custom.ThrowableUtils.throwIf;
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
        WrappedHttpServletResponse wrappedResponse = new WrappedHttpServletResponse(response);

        try {
            String token = request.getHeader("token");
            Authentication authenticatedObject;

            String requestURI = request.getRequestURI();
            String method = request.getMethod();

            if (isPermitAllEndpoint(requestURI, method)) {
                filterChain.doFilter(request, wrappedResponse);
                log.info("Response: " + wrappedResponse.getCaptureAsString());
                return;
            }

            throwIf(INVALID_HEADER.getError(), () -> isNull(token));

            UserModel authorizedUser = userServiceClient.getAccessToken(token);

            CustomAuthentication customAuthentication = authenticationMapper.map(authorizedUser);
            authenticatedObject = customAuthenticationManager.authenticate(customAuthentication);

            SecurityContextHolder.getContext().setAuthentication(authenticatedObject);

        } catch (ServiceException e) {
            e.printStackTrace();
            wrappedResponse.resetBuffer();
            wrappedResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            wrappedResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            wrappedResponse.getOutputStream().print(e.getError().toString());
            wrappedResponse.flushBuffer();
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
            wrappedResponse.resetBuffer();
            wrappedResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            wrappedResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            wrappedResponse.getOutputStream().print("{\"message\": \"" + e.getMessage() + "\"}");
            wrappedResponse.flushBuffer();
            return;
        }

        try {
            filterChain.doFilter(request, wrappedResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            wrappedResponse.resetBuffer();
            wrappedResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            wrappedResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            wrappedResponse.setCharacterEncoding("UTF-8");

            ServletOutputStream out = wrappedResponse.getOutputStream();
            out.write(e.getMessage().getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            return;
        }

        log.info("Response: " + wrappedResponse.getCaptureAsString());
        response.getOutputStream().write(wrappedResponse.getCaptureAsBytes());
    }

    private boolean isPermitAllEndpoint(String requestURI, String method) {
        return securityPropertiesConfig.getPaths().stream()
                .anyMatch(pair -> requestURI.startsWith(pair.getUrl()) && pair.getMethod().equals(method));
    }
}
