package com.sec.gen.next.serviceorchestrator.internal.places.mapper;

import com.next.gen.api.User;
import com.next.gen.sec.model.UserModel;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map(UserModel userModel);
    UserModel map(User user);
}
