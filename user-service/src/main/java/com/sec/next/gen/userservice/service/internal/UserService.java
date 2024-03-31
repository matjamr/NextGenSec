package com.sec.next.gen.userservice.service.internal;

import com.next.gen.sec.model.UserModel;

public interface UserService {
    UserModel findUserByEmail(String email);
    UserModel saveUser(UserModel userModel);
    UserModel updateUser(UserModel userModel);
}
