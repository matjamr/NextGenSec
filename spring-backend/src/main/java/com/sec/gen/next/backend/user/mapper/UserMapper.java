package com.sec.gen.next.backend.user.mapper;

import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.internal.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User from(UserModel userModel);
    UserModel from(User user);
}
