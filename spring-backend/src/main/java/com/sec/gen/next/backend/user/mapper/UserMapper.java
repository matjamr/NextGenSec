package com.sec.gen.next.backend.user.mapper;

import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.internal.User;
import com.sec.gen.next.backend.common.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AddressMapper.class})
public interface UserMapper {
    User from(UserModel userModel);

    @Mapping(target = "password", ignore = true)
    UserModel from(User user);
}
