package com.sec.gen.next.backend.internal.user.config;


import com.sec.gen.next.backend.internal.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.api.internal.User;
import com.sec.gen.next.backend.internal.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.internal.image.repository.ImageRepository;
import com.sec.gen.next.backend.internal.places.repository.PlacesRepository;
import com.sec.gen.next.backend.internal.product.repository.ProductRepository;
import com.sec.gen.next.backend.internal.security.builder.Builder;
import com.sec.gen.next.backend.internal.user.builders.ClaimsToUserBuilder;
import com.sec.gen.next.backend.internal.user.builders.UserPlaceAssignmentToDbBuilder;
import com.sec.gen.next.backend.internal.user.builders.UserToDbBuilder;
import com.sec.gen.next.backend.internal.user.mapper.SensitiveDataMapper;
import com.sec.gen.next.backend.internal.user.mapper.UserMapper;
import com.sec.gen.next.backend.internal.user.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.backend.internal.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.internal.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.internal.user.repository.UserRepository;
import com.sec.gen.next.backend.internal.user.service.UserService;
import com.sec.gen.next.backend.internal.user.service.UserServiceImpl;
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
            final UserMapper userMapper,
            final SensitiveDataRepository sensitiveDataRepository,
            final SensitiveDataMapper sensitiveDataMapper,
            final ProductRepository productRepository,
            final ImageRepository imageRepository
    ) {
        return new UserServiceImpl(userRepository,
                userPlaceAssignmentRepository,
                placesRepository,
                usertoDbBuilder,
                claimsToUserBuilder,
                userMapper,
                sensitiveDataRepository,
                sensitiveDataMapper,
                imageRepository,
                productRepository);
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
