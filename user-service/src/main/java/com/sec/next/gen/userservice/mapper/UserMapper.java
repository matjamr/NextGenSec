package com.sec.next.gen.userservice.mapper;

import com.next.gen.sec.model.UserModel;
import com.sec.next.gen.userservice.api.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserModel map(User user);
    User map(UserModel userModel);
}
