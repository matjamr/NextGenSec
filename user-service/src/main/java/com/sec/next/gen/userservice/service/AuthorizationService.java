package com.sec.next.gen.userservice.service;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.Source;
import reactor.core.publisher.Mono;

public interface AuthorizationService {
    Mono<AuthorizedUser> getUserInfo(String idToken, Source source);
}
