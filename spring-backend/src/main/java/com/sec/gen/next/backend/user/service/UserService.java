package com.sec.gen.next.backend.user.service;


import com.sec.gen.next.backend.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.api.internal.ClaimsUser;
import com.sec.gen.next.backend.api.internal.RegisterSource;
import com.sec.gen.next.backend.api.internal.User;

public interface UserService {
    void save(User userModel);
    User update(AdditionalInformationUpdateModel additionalInformationUpdateModel);

    User findUserByEmail(String email);
    User loginUser(ClaimsUser email, RegisterSource s);

    User verify(ClaimsUser claimsUser);
}
