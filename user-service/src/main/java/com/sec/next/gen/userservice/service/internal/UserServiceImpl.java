package com.sec.next.gen.userservice.service.internal;

import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.api.User;
import com.sec.next.gen.userservice.mapper.UserMapper;
import com.sec.next.gen.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.sec.next.gen.userservice.config.Error.*;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final List<BiConsumer<User, UserModel>> updateUserModelsConsumerList;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);


    @Override
    public UserModel findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::map)
                .orElseThrow(NO_USER_WITH_EMAIL::getError);
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        if(userRepository.existsUserByEmail(userModel.getEmail())) {
            throw USER_EXISTS.getError();
        }

        if(RegistrationSource.JWT.equals(userModel.getSource())) {
            Optional.ofNullable(userModel.getPassword())
                    .map(passwordEncoder::encode)
                    .ifPresentOrElse(userModel::setPassword,
                            () -> userModel
                                    .password(passwordEncoder.encode(RandomStringUtils.random(10)))
                                    .passwordChange(true));
        }

        return Optional.ofNullable(userRepository.save(userMapper.map(userModel)))
                            .map(userMapper::map)
                            .orElseThrow(INVALID_USER_DATA::getError);
    }

    @Override
    public UserModel updateUser(UserModel userModel) {

        User user = userRepository.findByEmail(userModel.getEmail())
                .orElseThrow(NO_USER_WITH_EMAIL::getError);

        updateUserModelsConsumerList
                .forEach(consumer -> consumer.accept(user, userModel));

        userRepository.save(user);

        return userMapper.map(user);
    }
}
