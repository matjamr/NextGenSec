package com.sec.gen.next.backend.user.builders;


import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;

import java.time.LocalDateTime;

public class ClaimsToUserBuilder implements Builder<AuthorizedUser, User> {


    @Override
    public User apply(AuthorizedUser claimsUser) {
        return new User()
                .setName(claimsUser.getGiven_name())
                .setEmail(claimsUser.getEmail())
                .setSurname(claimsUser.getFamily_name())
                .setPictureUrl(claimsUser.getPicture())
                .setCreationDate(LocalDateTime.now());
    }
}
