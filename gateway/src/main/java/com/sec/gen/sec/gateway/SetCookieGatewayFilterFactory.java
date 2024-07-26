package com.sec.gen.sec.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class SetCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SetCookieGatewayFilterFactory.Config> {

    public SetCookieGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();

            if (headers.containsKey("access_token")) {
                if (Objects.equals(headers.getFirst("access_token"), "REMOVED")) {
                    ResponseCookie refreshTokenCookie = ResponseCookie.from("access_token", null)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(0)
                            .build();

                    response.addCookie(refreshTokenCookie);
                } else {

                    String accessToken = headers.getFirst("access_token");
                    ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", accessToken.replace("Bearer ", ""))
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(24 * 60 * 60)
                            .build();
                    response.addCookie(accessTokenCookie);
                }
            }

            if (headers.containsKey("refresh_token")) {
                if (Objects.equals(headers.getFirst("refresh_token"), "REMOVED")) {
                    ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", null)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(0)
                            .build();

                    response.addCookie(refreshTokenCookie);
                } else {
                    String refreshToken = headers.getFirst("refresh_token");
                    ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken.replace("Bearer ", ""))
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(30 * 24 * 60 * 60)
                            .build();

                    response.addCookie(refreshTokenCookie);

                }
            }

            response.getHeaders().remove("refresh_token");
            response.getHeaders().remove("access_token");
        }));

    }

    public static class Config {
    }
}
