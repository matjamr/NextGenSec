package com.sec.next.gen.userservice.service.internal.authorization.providers;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import reactor.core.publisher.Mono;

public interface AuthorizationService {
    GoogleAuthorizedUser getUserInfo(String idToken);
}
