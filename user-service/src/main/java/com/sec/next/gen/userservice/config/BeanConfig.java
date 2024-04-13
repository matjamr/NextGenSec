package com.sec.next.gen.userservice.config;


import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.controller.RegistrationSourceDispatcher;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.internal.authorization.client.ExternalClient;
import com.sec.next.gen.userservice.service.internal.authorization.client.ExternalClientProvider;
import com.sec.next.gen.userservice.service.internal.authorization.client.FacebookExternalClient;
import com.sec.next.gen.userservice.service.internal.authorization.client.JwtExternalClient;
import com.sec.next.gen.userservice.service.internal.authorization.providers.AuthorizationService;
import com.sec.next.gen.userservice.service.internal.authorization.token.KeyProvider;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenBuilder;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenGenerator;
import com.sec.next.gen.userservice.service.external.providers.FacebookApiClient;
import com.sec.next.gen.userservice.service.internal.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class BeanConfig {

    @Bean
    public Function<RegistrationSource, AuthorizationService> sourceAuthorizationServiceDispatcher(
//            AuthorizationService googleUserInfoService,
            AuthorizationService jwtService
    ) {
        return new RegistrationSourceDispatcher(Map.of(
                RegistrationSource.GOOGLE, jwtService,
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

    @Bean
    public Supplier<Key> keyProvider(
            @Value("${jwt.secret}") String secret
    ) {
        return new KeyProvider(secret);
    }

    @Bean
    public Function<TokenContext, String> tokenGenerator(
            final Function<RegistrationSource, ExternalClient> externalClientProvider,
            final Function<GoogleAuthorizedUser, String> tokenBuilder

    ) {
        return new TokenGenerator(externalClientProvider, tokenBuilder);
    }

    @Bean
    public Function<RegistrationSource, ExternalClient> externalClientProvider(
            final ExternalClient jwtExternalClient,
            final ExternalClient facebookExternalClient
    ) {
        return new ExternalClientProvider(Map.of(
                RegistrationSource.JWT, jwtExternalClient,
                RegistrationSource.FACEBOOK, facebookExternalClient
        ));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public ExternalClient jwtExternalClient(
            final UserRepository userRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder
            ) {
        return new JwtExternalClient(userRepository, bCryptPasswordEncoder);
    }

    @Bean
    public ExternalClient facebookExternalClient(
            final FacebookApiClient facebookApiClient,
            final UserRepository userRepository
            ) {
        return new FacebookExternalClient(facebookApiClient, userRepository);
    }

    @Bean
    public Function<GoogleAuthorizedUser, String> tokenBuilder(
            final Supplier<Key> keyProvider
            ) {
        return new TokenBuilder(keyProvider);
    }
}
