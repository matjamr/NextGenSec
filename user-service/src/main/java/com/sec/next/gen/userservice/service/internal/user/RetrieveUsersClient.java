package com.sec.next.gen.userservice.service.internal.user;

import com.next.gen.sec.model.Role;
import com.next.gen.sec.model.UserModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import com.sec.next.gen.userservice.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import com.sec.next.gen.userservice.config.ServiceError;
import com.sec.next.gen.userservice.mapper.UserMapper;
import org.springframework.stereotype.Component;

import static com.sec.next.gen.userservice.config.Error.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class RetrieveUsersClient implements Function<String, List<UserModel>> {
    private final UserRepository userRepository;
    private final Function<String, UserModel> fromTokenUserProvider;
    private final UserMapper userMapper;

    @Override
    public List<UserModel> apply(String token) {
        UserModel user = fromTokenUserProvider.apply(token);

        if(user == null || user.getRole().equals(Role.USER)) {
            throw new ServiceError(UNAUTHORIZED);
        }

        if(user.getRole().equals(Role.SYSTEM)) {
            return userRepository.findAll()
                    .stream()
                    .map(userMapper::map)
                    .toList();
        }

        return userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .filter(u -> Objects.nonNull(u.getRole()))
                .filter(u -> u.getRole().equals(Role.USER))
                .toList();
    }
}
