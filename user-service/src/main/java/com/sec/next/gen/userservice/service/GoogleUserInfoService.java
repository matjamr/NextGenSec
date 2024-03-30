package com.sec.next.gen.userservice.service;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.Source;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GoogleUserInfoService implements AuthorizationService {

    private final WebClient webClient;

    public GoogleUserInfoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://oauth2.googleapis.com/tokeninfo").build();
    }

    @Override
    public Mono<AuthorizedUser> getUserInfo(String idToken, Source source) {
        return webClient.get()
                .uri("?id_token={id_token}", idToken)
                .retrieve()
                .bodyToMono(AuthorizedUser.class)
                .map(user -> user.setSource(String.valueOf(source)));
    }
}
