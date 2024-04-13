package com.sec.next.gen.userservice.service.internal.authorization.providers;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.sec.next.gen.userservice.service.external.providers.GoogleApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkedinService implements AuthorizationService {

    private final GoogleApiClient googleApiClient;

    @Override
    public GoogleAuthorizedUser getUserInfo(String idToken) {
        return googleApiClient.getUserInfo(idToken);
    }
}
