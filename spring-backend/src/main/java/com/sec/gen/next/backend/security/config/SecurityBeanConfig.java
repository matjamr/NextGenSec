package com.sec.gen.next.backend.security.config;

import com.sec.gen.next.backend.security.builder.SupportedRegistrationSources;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
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

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS");
//            }
//        };
//    }
@Bean
CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final String headers =  "Authorization, Access-Control-Allow-Headers, "+
            "Origin, Accept, X-Requested-With, Content-Type, " +
            "Access-Control-Request-Method, Custom-Filter-Header";

    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE")); // Required for PUT method
    config.addExposedHeader(headers);
    config.setAllowCredentials(true);
    config.applyPermitDefaultValues();

    source.registerCorsConfiguration("/**", config);

    return source;
}
}
