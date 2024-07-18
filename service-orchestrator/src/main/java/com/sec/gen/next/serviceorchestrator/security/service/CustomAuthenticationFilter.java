package com.sec.gen.next.serviceorchestrator.security.service;

import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import com.sec.gen.next.serviceorchestrator.exception.Error;
import com.sec.gen.next.serviceorchestrator.exception.ServiceException;
import com.sec.gen.next.serviceorchestrator.external.UserServiceClient;
import com.sec.gen.next.serviceorchestrator.security.config.SecurityPropertiesConfig;
import com.sec.gen.next.serviceorchestrator.security.config.WrappedHttpServletResponse;
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
import java.util.HashMap;
import java.util.Map;

import static com.next.gen.api.custom.ThrowableUtils.throwIf;
import static com.sec.gen.next.serviceorchestrator.exception.Error.INTERNAL_SERVER_ERROR;
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
            if (processAndValidateRequest(request, response, filterChain, wrappedResponse)) return;
        } catch (ServiceException e) {
            returnError(wrappedResponse, e.getError());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            returnError(wrappedResponse, INTERNAL_SERVER_ERROR.withFormattedError(e.getMessage()));
            return;
        }

        try {
            filterChain.doFilter(request, wrappedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            returnError(wrappedResponse, INTERNAL_SERVER_ERROR.withFormattedError(e.getMessage()));
            return;
        }

        log.info("Response: " + wrappedResponse.getCaptureAsString());
        response.getOutputStream().write(wrappedResponse.getCaptureAsBytes());
    }

    private boolean processAndValidateRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, WrappedHttpServletResponse wrappedResponse) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        Authentication authenticatedObject;
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (isPermitAllEndpoint(requestURI, method)) {
            filterChain.doFilter(request, wrappedResponse);
            log.info("Response: " + wrappedResponse.getCaptureAsString());
            response.getOutputStream().write(wrappedResponse.getCaptureAsBytes());
            return true;
        }

        throwIf(INVALID_HEADER.getError(), () -> isNull(token));

        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            headers.put(headerName, request.getHeader(headerName));
        });

        UserModel authorizedUser = userServiceClient.getAccessToken(token, headers);

        CustomAuthentication customAuthentication = authenticationMapper.map(authorizedUser);
        authenticatedObject = customAuthenticationManager.authenticate(customAuthentication);

        SecurityContextHolder.getContext().setAuthentication(authenticatedObject);
        return false;
    }

    private void returnError(WrappedHttpServletResponse wrappedResponse, Error e) throws IOException {
        wrappedResponse.resetBuffer();
        wrappedResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        wrappedResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        wrappedResponse.setCharacterEncoding("UTF-8");
        log.info("Response: " + wrappedResponse.getCaptureAsString());
        wrappedResponse.getOutputStream().print(e.toString());
        wrappedResponse.flushBuffer();
    }

    private boolean isPermitAllEndpoint(String requestURI, String method) {
        return securityPropertiesConfig.getPaths().stream()
                .anyMatch(pair -> requestURI.matches(pair.getUrl()) &&
                        pair.getMethod().equals(method) &&
                        pair.getAccess().equals("permitAll"));
    }
}
