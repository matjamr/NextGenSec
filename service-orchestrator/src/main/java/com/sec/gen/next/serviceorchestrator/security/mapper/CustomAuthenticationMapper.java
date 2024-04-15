package com.sec.gen.next.serviceorchestrator.security.mapper;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.UserModel;
import com.sec.gen.next.serviceorchestrator.api.CustomAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomAuthenticationMapper {

    CustomAuthentication map(UserModel user);
}
