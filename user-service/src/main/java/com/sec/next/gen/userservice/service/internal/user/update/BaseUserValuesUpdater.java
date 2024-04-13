package com.sec.next.gen.userservice.service.internal.user.update;

import com.next.gen.sec.model.UserModel;
import com.next.gen.api.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.function.BiConsumer;


public class BaseUserValuesUpdater implements BiConsumer<User, UserModel> {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @Override
    public void accept(User user, UserModel userModel) {
        Optional.ofNullable(userModel.getName())
                .ifPresent(user::setName);

        Optional.ofNullable(userModel.getSurname())
                .ifPresent(user::setSurname);

        Optional.ofNullable(userModel.getPhoneNumber())
                .ifPresent(user::setPhoneNumber);

        Optional.ofNullable(userModel.getPassword())
                .map(passwordEncoder::encode)
                .ifPresent(encodedPassword -> user
                        .setPassword(encodedPassword)
                        .setPasswordChange(false)
                );
    }
}
