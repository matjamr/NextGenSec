package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.service.internal.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class FromTokenUserProvider implements Function<String, UserModel> {
    private final Function<String, String> usernameExtractor;
    private final UserService userService;

    @Override
    public UserModel apply(String token) {
        String username = usernameExtractor.apply(token);

        return userService.findUserByEmail(username);
    }
}
