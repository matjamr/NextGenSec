package com.sec.gen.sec.gateway;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class CustomTokenVerificationGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomTokenVerificationGatewayFilterFactory.Config> {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private final String authServiceUrl;

    private final Set<Pair<Pattern, HttpMethod>> permitAllPatterns = Set.of(
            Pair.of(Pattern.compile("/api/product"), GET),
            Pair.of(Pattern.compile("/api/news"), GET),
            Pair.of(Pattern.compile("/api/image.*"), GET),
            Pair.of(Pattern.compile("/api-docs"), GET),
            Pair.of(Pattern.compile("/api/user.*"), POST),
            Pair.of(Pattern.compile("/api/user.*"), GET)
    );

    public CustomTokenVerificationGatewayFilterFactory(
            @Value("${external.auth-service.url}") String authServiceUrl
    ) {
        super(Config.class);
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            Map<String, List<HttpCookie>> cookies = exchange.getRequest().getCookies();

            String accessToken = getCookieValue(cookies, "access_token");
            String refreshToken = getCookieValue(cookies, "refresh_token");

            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            HttpMethod method = request.getMethod();

            request.mutate().header("Authorization", "Bearer " + Optional.ofNullable(accessToken).orElse(refreshToken)).build();

            if (isPermitAll(path, method)) {
                return chain.filter(exchange);
            }

            return webClientBuilder.build()
                    .post()
                    .uri(this.authServiceUrl + "/api/user/security/verify")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .flatMap(response -> chain.filter(exchange))
                    .onErrorResume(e -> handleError(exchange, "Token verification failed"));
        };
    }

    private String getCookieValue(Map<String, List<HttpCookie>> cookies, String cookieName) {
        List<HttpCookie> cookieList = cookies.get(cookieName);
        if (cookieList != null && !cookieList.isEmpty()) {
            return cookieList.get(0).getValue();
        }
        return null;
    }

    private Mono<Void> handleError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

        String errorJson = String.format("{\"message\": \"%s\"}", errorMessage);
        DataBuffer buffer = response.bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
    }

    private boolean isPermitAll(String path, HttpMethod method) {
        return permitAllPatterns.stream().anyMatch(pair -> pair.getLeft().matcher(path).matches()
                && pair.getRight() == method);
    }
}

