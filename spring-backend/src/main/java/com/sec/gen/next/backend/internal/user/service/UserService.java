package com.sec.gen.next.backend.internal.user.service;


import com.sec.gen.next.backend.internal.api.external.AdditionalInformationUpdateModel;
import com.sec.gen.next.backend.internal.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.internal.api.external.UserModel;
import com.sec.gen.next.backend.internal.api.external.UserPlaceModel;
import com.sec.gen.next.backend.internal.api.internal.ClaimsUser;
import com.sec.gen.next.backend.internal.api.internal.RegisterSource;
import com.sec.gen.next.backend.internal.api.internal.User;

import java.util.List;

public interface UserService {
    User save(UserModel userModel);
    User update(AdditionalInformationUpdateModel additionalInformationUpdateModel);
    User findUserByEmail(String email);
    User loginUser(ClaimsUser email, RegisterSource s);
    User verify(ClaimsUser claimsUser);
    List<UserModel> findAll();

    User verifyPlace(ClaimsUser attribute, UserPlaceModel userPlaceModel);

    List<SensitiveDataModel> addSensitiveData(ClaimsUser attribute, SensitiveDataModel sensitiveDataModel);

    List<SensitiveDataModel> getSensitiveData(String email);
}
