package com.sec.gen.next.backend.service.user.config;


import com.sec.gen.next.backend.security.api.internal.ClaimsUser;
import com.sec.gen.next.backend.security.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.service.user.builders.ClaimsToUserBuilder;
import com.sec.gen.next.backend.service.user.builders.UserToDbBuilder;
import com.sec.gen.next.backend.service.user.repository.UserRepository;
import com.sec.gen.next.backend.service.user.service.UserService;
import com.sec.gen.next.backend.service.user.service.UserServiceImpl;
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
