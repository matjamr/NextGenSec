package com.sec.next.gen.userservice.service.authorization;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.service.external.providers.GoogleExternalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleUserInfoService implements AuthorizationService {

    private final GoogleExternalClient googleExternalClient;

    @Override
    public GoogleAuthorizedUser getUserInfo(String idToken, RegistrationSource source) {
        return googleExternalClient.getUserInfo(idToken)
                .source(source);
    }
}
