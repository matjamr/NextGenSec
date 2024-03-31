package com.sec.next.gen.userservice.service.internal;

import com.next.gen.sec.model.UserModel;
import com.next.gen.api.User;import com.sec.next.gen.userservice.mapper.AddressMapper;
import com.sec.next.gen.userservice.mapper.UserMapper;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.external.kafka.KafkaProducer;
import com.sec.next.gen.userservice.service.internal.update.BaseUserValuesUpdater;
import com.sec.next.gen.userservice.service.internal.update.UserAddressUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.BiConsumer;

@Configuration("UserBeansConfig")
public class BeansConfig {

    @Bean
    public UserService userService(
            UserRepository userRepository,
            UserMapper userMapper,
            List<BiConsumer<User, UserModel>> updateUserModelsConsumerList,
            KafkaProducer kafkaProducer
    ) {
        return new UserServiceImpl(userRepository, userMapper, updateUserModelsConsumerList, kafkaProducer);
    }

    @Bean
    List<BiConsumer<User, UserModel>> updateUserModelsConsumerList(
            AddressMapper addressMapper
    ) {
        return List.of(
                new BaseUserValuesUpdater(),
                new UserAddressUpdater(addressMapper)
        );
    }
}
