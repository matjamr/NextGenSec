package com.sec.gen.next.backend.user.builders;



import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;

import java.time.LocalDateTime;

public class UserToDbBuilder implements Builder<User, User> {

    @Override
    public User apply(User user) {
        return user.setCreationDate(LocalDateTime.now());
    }

}
