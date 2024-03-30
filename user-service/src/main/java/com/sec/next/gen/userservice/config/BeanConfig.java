package com.sec.next.gen.userservice.config;


import com.sec.next.gen.userservice.controller.RegistrationSourceDispatcher;
import com.sec.next.gen.userservice.models.Source;
import com.sec.next.gen.userservice.service.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class BeanConfig {

    @Bean
    public Function<Source, AuthorizationService> sourceAuthorizationServiceDispatcher(
            AuthorizationService googleUserInfoService,
            AuthorizationService jwtService
    ) {
        return new RegistrationSourceDispatcher(Map.of(
                Source.GOOGLE, googleUserInfoService,
                Source.JWT, jwtService
        )
        );
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
