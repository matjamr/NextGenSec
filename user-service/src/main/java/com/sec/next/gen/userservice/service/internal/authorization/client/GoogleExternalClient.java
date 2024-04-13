package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.external.providers.GoogleApiClient;
import com.sec.next.gen.userservice.service.internal.authorization.providers.AuthorizationService;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import lombok.RequiredArgsConstructor;

import static com.sec.next.gen.userservice.config.Error.NO_USER_WITH_EMAIL;

@RequiredArgsConstructor
public class GoogleExternalClient implements ExternalClient {

    private final GoogleApiClient googleApiClient;
    private final UserRepository userRepository;

    @Override
    public GoogleAuthorizedUser getAndVerify(TokenContext tokenContext) {
        return BetterOptional.of(get(tokenContext))
                .peek((usr) -> userRepository.findByEmail(usr.getEmail())
                        .orElseThrow(NO_USER_WITH_EMAIL::getError))
                .orElseThrow(NO_USER_WITH_EMAIL::getError);
    }

    @Override
    public GoogleAuthorizedUser get(TokenContext tokenContext) {
        return googleApiClient.getUserInfo(tokenContext.getToken()).source(RegistrationSource.GOOGLE);
    }
}
