package com.sec.next.gen.userservice.mapper;

import com.next.gen.sec.model.UserModel;
import com.next.gen.api.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserModel map(User user);

    @Mapping(source = "role", target = "role", defaultValue = "USER")
    User map(UserModel userModel);
}
