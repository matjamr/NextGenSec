package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;

public interface ExternalClient {
    GoogleAuthorizedUser getAndVerify(TokenContext tokenContext);
    GoogleAuthorizedUser get(TokenContext tokenContext);
}
