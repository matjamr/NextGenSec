package com.sec.next.gen.userservice.mapper;

import com.next.gen.sec.model.UserModel;
import com.next.gen.api.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserModel map(User user);
    User map(UserModel userModel);
}
