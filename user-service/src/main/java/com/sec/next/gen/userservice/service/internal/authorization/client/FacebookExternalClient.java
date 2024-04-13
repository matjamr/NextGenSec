package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.api.custom.BetterOptional;
import com.next.gen.sec.model.FacebookUserResponse;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import com.sec.next.gen.userservice.service.external.providers.FacebookApiClient;
import com.sec.next.gen.userservice.service.internal.user.UserService;
import lombok.RequiredArgsConstructor;

import static com.sec.next.gen.userservice.config.Error.NO_USER_WITH_EMAIL;

@RequiredArgsConstructor
public class FacebookExternalClient implements ExternalClient {
    private final FacebookApiClient facebookApiClient;
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
        FacebookUserResponse facebookUserResponse = facebookApiClient.getUserInfo(tokenContext.getToken(), "id,email,first_name,last_name");

        return new GoogleAuthorizedUser()
                .email(facebookUserResponse.getEmail())
                .familyName(facebookUserResponse.getLastName())
                .givenName(facebookUserResponse.getFirstName())
                .source(RegistrationSource.FACEBOOK)
                .email("modkil265@gmail.com");
    }
}
