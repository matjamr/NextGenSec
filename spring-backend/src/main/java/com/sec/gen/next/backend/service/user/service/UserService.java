package com.sec.gen.next.backend.service.user.service;


import com.sec.gen.next.backend.security.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.security.api.internal.ClaimsUser;
import com.sec.gen.next.backend.security.api.internal.RegisterSource;
import com.sec.gen.next.backend.security.api.internal.User;

public interface UserService {
    void save(User userModel);
    User update(AdditionalInformationUpdateModel additionalInformationUpdateModel);

    User findUserByEmail(String email);
    User loginUser(ClaimsUser email, RegisterSource s);

    User verify(ClaimsUser claimsUser);
}
