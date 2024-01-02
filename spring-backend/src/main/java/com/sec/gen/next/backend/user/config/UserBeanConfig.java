package com.sec.gen.next.backend.user.config;


import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.builders.ClaimsToUserBuilder;
import com.sec.gen.next.backend.user.builders.UserPlaceAssignmentToDbBuilder;
import com.sec.gen.next.backend.user.builders.UserToDbBuilder;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import com.sec.gen.next.backend.user.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.user.service.UserService;
import com.sec.gen.next.backend.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;


@Configuration
public class UserBeanConfig {

    @Bean
    public UserService userService(
            final UserRepository userRepository,
            final PlacesRepository placesRepository,
            final UserPlaceAssignmentRepository userPlaceAssignmentRepository,
            final @Qualifier("usertoDbBuilder") Builder<User, User> usertoDbBuilder,
            final Builder<ClaimsUser, User> claimsToUserBuilder,
            final UserMapper userMapper
            ) {
        return new UserServiceImpl(userRepository,
                userPlaceAssignmentRepository,
                placesRepository,
                usertoDbBuilder,
                claimsToUserBuilder,
                userMapper);
    }

    @Bean
    public Builder<User, User> usertoDbBuilder() {
        return new UserToDbBuilder();
    }

    @Bean
    public Builder<ClaimsUser, User> claimsToUserBuilder() {
        return new ClaimsToUserBuilder();
    }

    @Bean("userPlaceAssignmentToDbBuilder")
    public Function<List<UserPlaceAssignmentModel>, List<UserPlaceAssignment>> userPlaceAssignmentToDbBuilder(
            final UserPlaceAssignmentRepository userPlaceAssignmentRepository,
            final UserPlaceAssignmentMapper userPlaceAssignmentMapper
            ) {
        return new UserPlaceAssignmentToDbBuilder(userPlaceAssignmentRepository, userPlaceAssignmentMapper);
    }
}
