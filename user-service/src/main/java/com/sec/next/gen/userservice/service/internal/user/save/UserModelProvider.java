package com.sec.next.gen.userservice.service.internal.user.save;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.authorization.client.ExternalClient;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import com.sec.next.gen.userservice.service.internal.user.SaveUserContext;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class UserModelProvider implements Function<SaveUserContext, UserModel> {

    private final Function<RegistrationSource, ExternalClient> externalClientProvider;

    @Override
    public UserModel apply(SaveUserContext context) {
        ExternalClient externalClient = externalClientProvider.apply(context.getRegistrationSource());

        GoogleAuthorizedUser googleAuthorizedUser = externalClient.get(new TokenContext()
                .setSource(context.getRegistrationSource())
                .setToken(context.getToken()));

        return new UserModel()
                .email(googleAuthorizedUser.getEmail())
                .source(context.getRegistrationSource())
                .passwordChange(false)
                .surname(googleAuthorizedUser.getFamilyName())
                .name(googleAuthorizedUser.getGivenName());
    }

}
