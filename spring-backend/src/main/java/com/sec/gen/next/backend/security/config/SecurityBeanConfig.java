package com.sec.gen.next.backend.security.config;

import com.sec.gen.next.backend.security.builder.SupportedRegistrationSources;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.function.Predicate;

@Configuration
public class SecurityBeanConfig {

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
