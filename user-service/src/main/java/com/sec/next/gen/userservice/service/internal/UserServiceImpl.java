package com.sec.next.gen.userservice.service.internal;

import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.mapper.UserMapper;
import com.sec.next.gen.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.sec.next.gen.userservice.config.Error.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

        return Optional.ofNullable(userRepository.save(userMapper.map(userModel)))
                            .map(userMapper::map)
                            .orElseThrow(INVALID_USER_DATA::getError);
    }
}
