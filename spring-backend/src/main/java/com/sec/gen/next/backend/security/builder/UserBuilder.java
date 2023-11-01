package com.sec.gen.next.backend.security.builder;

import com.sec.gen.next.backend.api.internal.ClaimsUser;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserBuilder implements Builder<Jwt, ClaimsUser> {

    private static final String EMAIL = "email";
    private static final String NAME = "given_name";
    private static final String FAMILY_NAME = "family_name";
    private static final String PICTURE = "picture";
    private static final String LOCALE = "locale";


    @Override
    public ClaimsUser apply(Jwt jwt) {
        return ClaimsUser.builder()
                .email(jwt.getClaim(EMAIL))
                .name(jwt.getClaim(NAME))
                .familyName(jwt.getClaim(FAMILY_NAME))
                .pictureUrl(jwt.getClaim(PICTURE))
                .locale(jwt.getClaim(LOCALE))
                .build();
    }
}
