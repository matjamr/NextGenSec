package com.sec.gen.next.backend.user.builders;


import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;

import java.time.LocalDateTime;

public class ClaimsToUserBuilder implements Builder<ClaimsUser, User> {


    @Override
    public User apply(ClaimsUser claimsUser) {
        return new User()
                .name(claimsUser.getName())
                .email(claimsUser.getEmail())
                .surname(claimsUser.getFamilyName())
                .pictureUrl(claimsUser.getPictureUrl())
                .creationDate(LocalDateTime.now());
    }
}
