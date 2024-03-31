package com.sec.next.gen.userservice.service.internal;

import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.api.User;
import reactor.core.publisher.Mono;

public interface UserService {
    UserModel findUserByEmail(String email);
    UserModel saveUser(UserModel userModel);
    UserModel updateUser(UserModel userModel);
}
