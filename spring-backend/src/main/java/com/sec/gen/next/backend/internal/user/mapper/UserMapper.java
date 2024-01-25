package com.sec.gen.next.backend.internal.user.mapper;

import com.sec.gen.next.backend.internal.api.external.UserModel;
import com.sec.gen.next.backend.internal.api.internal.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User from(UserModel userModel);
    UserModel from(User user);
}
