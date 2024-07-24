package com.next.gen.api.security;

import com.next.gen.sec.model.UserModel;
import org.mapstruct.Mapper;

@Mapper
public abstract class CustomAuthenticationMapper {
    public abstract CustomAuthentication map(UserModel user);
}
