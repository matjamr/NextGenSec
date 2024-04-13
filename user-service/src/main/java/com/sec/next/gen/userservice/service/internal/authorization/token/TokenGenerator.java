package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.service.internal.authorization.client.ExternalClient;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class TokenGenerator implements Function<TokenContext, String> {

    private final Function<RegistrationSource, ExternalClient> externalClientProvider;
    private final Function<GoogleAuthorizedUser, String> tokenBuilder;

    @Override
    public String apply(TokenContext tokenContext) {
        ExternalClient authorizationService = externalClientProvider.apply(tokenContext.getSource());
        GoogleAuthorizedUser user = authorizationService.getAndVerify(tokenContext);

        return tokenBuilder.apply(user);
    }
}
