package com.sec.gen.next.backend.security.config;

import com.sec.gen.next.backend.security.api.internal.ClaimsUser;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.security.builder.SupportedRegistrationSources;
import com.sec.gen.next.backend.security.builder.UserBuilder;
import com.sec.gen.next.backend.security.service.AuthenticationService;
import com.sec.gen.next.backend.service.user.service.UserService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.function.Predicate;

@Configuration
public class SecurityBeanConfig {

    @Bean(name = "authenticationServiceFilter")
    public Filter authenticationServiceFilter(
            final Builder<Jwt, ClaimsUser> claimsUserBuilder,
            final UserService userService,
            @Qualifier("supportedRegistrationSources") final Predicate<String> supportedRegistrationSources
            ) {
        return new AuthenticationService(claimsUserBuilder, userService, supportedRegistrationSources);
    }

    @Bean
    public Builder<Jwt, ClaimsUser> claimsUserBuilder() {
        return new UserBuilder();
    }

    @Bean
    public Predicate<String> supportedRegistrationSources(
            final SecurityPropertiesConfig securityPropertiesConfig
    ) {
        return new SupportedRegistrationSources(
                securityPropertiesConfig.getSupportedRegistrationSources()
        );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
