package com.sec.next.gen.userservice.service.internal.user;

import com.next.gen.sec.model.UserModel;

public interface UserService {
    UserModel findUserByEmail(String email);
    UserModel saveUser(SaveUserContext saveUserContext);
    UserModel updateUser(UserModel userModel);
}
