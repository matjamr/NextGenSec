package com.sec.gen.next.backend.user.builders;


import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;

import java.time.LocalDateTime;

public class ClaimsToUserBuilder implements Builder<AuthorizedUser, User> {


    @Override
    public User apply(AuthorizedUser claimsUser) {
        return new User()
                .name(claimsUser.getGiven_name())
                .email(claimsUser.getEmail())
                .surname(claimsUser.getFamily_name())
                .pictureUrl(claimsUser.getPicture())
                .creationDate(LocalDateTime.now());
    }
}
