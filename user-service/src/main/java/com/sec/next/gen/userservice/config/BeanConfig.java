package com.sec.next.gen.userservice.config;


import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.controller.RegistrationSourceDispatcher;
import com.sec.next.gen.userservice.service.authorization.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class BeanConfig {

    @Bean
    public Function<RegistrationSource, AuthorizationService> sourceAuthorizationServiceDispatcher(
            AuthorizationService googleUserInfoService,
            AuthorizationService jwtService
    ) {
        return new RegistrationSourceDispatcher(Map.of(
                RegistrationSource.GOOGLE, googleUserInfoService,
                RegistrationSource.JWT, jwtService
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
