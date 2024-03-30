package com.sec.gen.next.backend.user.builders.update;

import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.external.OutboundEmailModel;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.common.kafka.KafkaOutboundEmailProducer;
import com.sec.gen.next.backend.user.repository.SensitiveDataRepository;
import com.sec.gen.next.backend.user.service.Executor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UserDataResolver implements Executor<User, UserModel> {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
    private final KafkaOutboundEmailProducer kafkaOutboundEmailProducer;

    @Override
    public boolean shouldAccept(User user, UserModel userModel) {
        return nonNull(userModel.getName()) ||
                nonNull(userModel.getSurname()) ||
                nonNull(userModel.getPhoneNumber()) ||
                nonNull(userModel.getEmail()) ||
                nonNull(userModel.getPassword());
    }

    @Transactional
    @Override
    public void accept(User user, UserModel userModel) {

        Optional.ofNullable(userModel.getName())
                .ifPresent(user::setName);

        Optional.ofNullable(userModel.getSurname())
                .ifPresent(user::setSurname);

        Optional.ofNullable(userModel.getPhoneNumber())
                .ifPresent(user::setPhoneNumber);

        Optional.ofNullable(userModel.getPassword())
                .ifPresent(user::setPassword);

        if(nonNull(userModel.getPassword())) {
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            user.setPasswordChange("false");

            kafkaOutboundEmailProducer.sendMessage(new OutboundEmailModel()
                    .setEmail(userModel.getEmail())
                    .setParams(Map.of("email", userModel.getEmail()))
                    .setStrategy("NEW_PASSWORD"));
        }
    }
}