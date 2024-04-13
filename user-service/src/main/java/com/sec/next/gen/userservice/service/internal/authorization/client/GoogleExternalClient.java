package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.sec.next.gen.userservice.service.external.providers.GoogleApiClient;
import com.sec.next.gen.userservice.service.internal.authorization.providers.AuthorizationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleExternalClient implements AuthorizationService {

    private final GoogleApiClient googleApiClient;

    @Override
    public GoogleAuthorizedUser getUserInfo(String idToken) {
        return googleApiClient.getUserInfo(idToken);
    }
}
