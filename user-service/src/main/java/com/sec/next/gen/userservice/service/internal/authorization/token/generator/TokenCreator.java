package com.sec.next.gen.userservice.service.internal.authorization.token.generator;

import com.sec.next.gen.userservice.service.internal.authorization.token.TokenParams;

public interface TokenCreator {
    String createToken(TokenParams tokenParams);
}

