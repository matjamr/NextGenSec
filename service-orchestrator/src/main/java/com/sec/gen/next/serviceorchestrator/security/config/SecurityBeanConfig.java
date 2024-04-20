package com.sec.gen.next.serviceorchestrator.security.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.function.Predicate;

@Configuration
public class SecurityBeanConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final String headers = "Authorization, Access-Control-Allow-Headers, " +
                "Origin, Accept, X-Requested-With, Content-Type, " +
                "Access-Control-Request-Method, Custom-Filter-Header";

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.addExposedHeader(headers);
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
