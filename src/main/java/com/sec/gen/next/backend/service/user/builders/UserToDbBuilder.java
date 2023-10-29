package com.sec.gen.next.backend.service.user.builders;



import com.sec.gen.next.backend.security.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;

import java.time.LocalDateTime;

public class UserToDbBuilder implements Builder<User, User> {

    @Override
    public User apply(User user) {
        return user.creationDate(LocalDateTime.now());
    }

}
