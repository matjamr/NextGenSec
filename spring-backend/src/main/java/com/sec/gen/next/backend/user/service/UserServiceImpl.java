package com.sec.gen.next.backend.user.service;

import com.sec.gen.next.backend.api.exception.Error;
import com.sec.gen.next.backend.api.exception.ServiceException;
import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.RegisterSource;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.security.builder.Builder;
import com.sec.gen.next.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Builder<User, User> usertoDbBuilder;
    private final Builder<ClaimsUser, User> claimsToUserBuilder;


    @Override
    public void save(User userModel) {

    }

    @Override
    public User update(AdditionalInformationUpdateModel additionalInformationUpdateModel) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    @Override
    public User loginUser(ClaimsUser claimsUser, RegisterSource s) {
        userRepository.findUserByEmail(claimsUser.getEmail())
                .ifPresentOrElse(user -> {
                    userRepository.save(usertoDbBuilder.apply(user));
                }, () -> registerUser(claimsUser));

        return null;
    }

    @Override
    public User verify(ClaimsUser claimsUser) {
        return userRepository.findUserByEmail(claimsUser.getEmail())
                .orElseThrow(() -> new ServiceException(Error.INVALID_USER_DATA));
    }

    private void registerUser(ClaimsUser claimsUser) {
        userRepository.save(usertoDbBuilder.apply(
                claimsToUserBuilder.apply(claimsUser)));
    }
}
