package com.sec.next.gen.userservice.service.internal;

import com.next.gen.api.User;
import com.next.gen.sec.model.OutboundEmailModel;
import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.mapper.UserMapper;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.external.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import static com.sec.next.gen.userservice.config.Error.*;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final List<BiConsumer<User, UserModel>> updateUserModelsConsumerList;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
    private final KafkaProducer kafkaProducer;


    @Override
    public UserModel findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::map)
                .orElseThrow(NO_USER_WITH_EMAIL::getError);
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        final String[] tmpPasswdChange = {null};

        if (userRepository.existsUserByEmail(userModel.getEmail())) {
            throw USER_EXISTS.getError();
        }

        if (RegistrationSource.JWT.equals(userModel.getSource())) {
            Optional.ofNullable(userModel.getPassword())
                    .map(passwordEncoder::encode)
                    .ifPresentOrElse(userModel::setPassword,
                            () -> {
                                tmpPasswdChange[0] = RandomStringUtils.random(10);
                                userModel
                                        .password(passwordEncoder.encode(tmpPasswdChange[0]))
                                        .passwordChange(true);
                            });
        }

        UserModel savedUser = Optional.ofNullable(userMapper.map(userModel))
                .map(userRepository::save)
                .map(userMapper::map)
                .orElseThrow(INVALID_USER_DATA::getError);

        kafkaProducer.sendMessage(new OutboundEmailModel()
                .email(userModel.getEmail())
                .params(Map.of("email", userModel.getEmail()))
                .strategy("ACCOUNT_CREATE"));

        if(tmpPasswdChange[0] != null)
            kafkaProducer.sendMessage(new OutboundEmailModel()
                    .email(userModel.getEmail())
                    .params(Map.of("email", userModel.getEmail(),
                            "password", tmpPasswdChange[0]))
                    .strategy("PASSWD_CHANGE"));

        return savedUser;
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
