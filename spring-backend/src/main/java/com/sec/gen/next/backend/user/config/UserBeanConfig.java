package com.sec.gen.next.backend.user.config;


import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.builders.ClaimsToUserBuilder;
import com.sec.gen.next.backend.user.builders.UserToDbBuilder;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.user.service.UserService;
import com.sec.gen.next.backend.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserBeanConfig {

    @Bean
    public UserService userService(
            final UserRepository userRepository,
            final @Qualifier("usertoDbBuilder") Builder<User, User> usertoDbBuilder,
            final Builder<ClaimsUser, User> claimsToUserBuilder
            ) {
        return new UserServiceImpl(userRepository,
                usertoDbBuilder,
                claimsToUserBuilder);
    }

    @Bean
    public Builder<User, User> usertoDbBuilder() {
        return new UserToDbBuilder();
    }

    @Bean
    public Builder<ClaimsUser, User> claimsToUserBuilder() {
        return new ClaimsToUserBuilder();
    }
}
