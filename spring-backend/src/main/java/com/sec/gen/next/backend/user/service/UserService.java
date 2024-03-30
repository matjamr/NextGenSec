package com.sec.gen.next.backend.user.service;


import com.sec.gen.next.backend.api.external.SensitiveDataModel;
import com.sec.gen.next.backend.api.external.UserModel;
import com.sec.gen.next.backend.api.external.UserPlaceModel;
import com.sec.gen.next.backend.api.internal.RegisterSource;
import com.sec.gen.next.backend.api.internal.User;

import java.util.List;

public interface UserService {
    User save(UserModel userModel, RegisterSource source);
    UserModel update(UserModel userModel);
    User findUserByEmail(String email);
    User loginUser(RegisterSource s);
    UserModel verify();
    List<UserModel> findAll(boolean placeRestriction);

    User verifyPlace(UserPlaceModel userPlaceModel);

    List<SensitiveDataModel> addSensitiveData(SensitiveDataModel sensitiveDataModel);

    List<SensitiveDataModel> getSensitiveData(String email);

    UserModel oauth2Login(RegisterSource source);
}
