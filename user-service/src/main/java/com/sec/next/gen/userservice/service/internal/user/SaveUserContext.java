package com.sec.next.gen.userservice.service.internal.user;

import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.UserModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SaveUserContext {
    private UserModel userModel;
    private String token;
    private RegistrationSource registrationSource;
}
