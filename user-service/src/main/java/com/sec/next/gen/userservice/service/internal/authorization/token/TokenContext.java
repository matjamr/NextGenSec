package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Data
@Accessors(chain = true)
public class TokenContext {
    private GoogleAuthorizedUser authorizedUser;
    private RegistrationSource source;
    private String token;
}
