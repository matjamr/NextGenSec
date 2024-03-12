package com.sec.gen.next.backend.user.config;


import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.external.AuthorizedUser;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.external.UserPlaceAssignmentModel;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import com.sec.gen.next.backend.common.address.AddressMapper;
import com.sec.gen.next.backend.common.address.AddressRepository;
import com.sec.gen.next.backend.common.kafka.KafkaOutboundEmailProducer;
import com.sec.gen.next.backend.image.repository.ImageRepository;
import com.sec.gen.next.backend.places.repository.PlacesRepository;
import com.sec.gen.next.backend.product.repository.ProductRepository;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.builders.ClaimsToUserBuilder;
import com.sec.gen.next.backend.user.builders.UserPlaceAssignmentToDbBuilder;
import com.sec.gen.next.backend.user.builders.UserToDbBuilder;
import com.sec.gen.next.backend.user.builders.update.AddressResolver;
import com.sec.gen.next.backend.user.builders.update.UserDataResolver;
import com.sec.gen.next.backend.user.mapper.SensitiveDataMapper;
import com.sec.gen.next.backend.user.mapper.UserMapper;
import com.sec.gen.next.backend.user.mapper.UserPlaceAssignmentMapper;
import com.sec.gen.next.backend.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.user.repository.UserPlaceAssignmentRepository;
import com.sec.gen.next.backend.user.repository.UserRepository;
import com.sec.gen.next.backend.user.service.Executor;
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
            final @Qualifier("usertoDbBuilder") Builder<User, User> usertoDbBuilder,
            final Builder<AuthorizedUser, User> claimsToUserBuilder,
            final UserMapper userMapper,
            final SensitiveDataRepository sensitiveDataRepository,
            final SensitiveDataMapper sensitiveDataMapper,
            final ProductRepository productRepository,
            final ImageRepository imageRepository,
            final Executor<User, UserModel> addressResolver,
            final Executor<User, UserModel> userDataResolver,
            final AddressRepository addressRepository,
            final KafkaOutboundEmailProducer kafkaOutboundEmailProducer
    ) {
        return new UserServiceImpl(userRepository,
                placesRepository,
                usertoDbBuilder,
                claimsToUserBuilder,
                userMapper,
                sensitiveDataRepository,
                sensitiveDataMapper,
                imageRepository,
                productRepository,
                List.of(
                        addressResolver,
                        userDataResolver
                ),
                addressRepository,
                kafkaOutboundEmailProducer);
    }

    @Bean
    public Executor<User, UserModel> addressResolver(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        return new AddressResolver(addressRepository, addressMapper);
    }

    @Bean
    public Executor<User, UserModel> userDataResolver(
            final KafkaOutboundEmailProducer kafkaOutboundEmailProducer
    ) {
        return new UserDataResolver(kafkaOutboundEmailProducer);
    }

    @Bean
    public Builder<User, User> usertoDbBuilder() {
        return new UserToDbBuilder();
    }

    @Bean
    public Builder<AuthorizedUser, User> claimsToUserBuilder() {
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
