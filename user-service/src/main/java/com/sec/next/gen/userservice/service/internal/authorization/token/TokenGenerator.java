package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.Token;
import com.sec.next.gen.userservice.service.internal.authorization.client.ExternalClient;
import com.sec.next.gen.userservice.service.internal.authorization.token.generator.TokenCreator;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class TokenGenerator implements Function<TokenContext, Token> {

    private final Function<RegistrationSource, ExternalClient> externalClientProvider;
    private final TokenCreator accessTokenBuilder;
    private final TokenCreator refreshTokenBuilder;

    @Override
    public Token apply(TokenContext tokenContext) {
        ExternalClient authorizationService = externalClientProvider.apply(tokenContext.getSource());
        GoogleAuthorizedUser user = authorizationService.getAndVerify(tokenContext);
        String accessToken = accessTokenBuilder.createToken(new TokenParams(user, 86400000L));
        String refreshToken = refreshTokenBuilder.createToken(new TokenParams(user, 86400000L * 30));

        return new Token().accessToken(accessToken).refreshToken(refreshToken);
    }
}
