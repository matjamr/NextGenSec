package com.sec.next.gen.userservice.service.internal.authorization.client;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.internal.authorization.token.TokenContext;
import com.sec.next.gen.userservice.service.internal.user.UserService;
import jakarta.ws.rs.NotSupportedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.sec.next.gen.userservice.config.Error.INVALID_USER_DATA;

@RequiredArgsConstructor
public class JwtExternalClient implements ExternalClient {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public GoogleAuthorizedUser getAndVerify(TokenContext tokenContext) {
        return userRepository.findByEmail(tokenContext.getAuthorizedUser().getEmail())
                .filter(user -> bCryptPasswordEncoder.matches(tokenContext.getAuthorizedUser().getPassword(), user.getPassword()))
                .map(user -> new GoogleAuthorizedUser()
                        .email(user.getEmail())
                        .givenName(user.getName())
                        .familyName(user.getSurname())
                        .source(RegistrationSource.JWT)
                ).orElseThrow(INVALID_USER_DATA::getError);
    }

    @Override
    public GoogleAuthorizedUser get(TokenContext tokenContext) {
        throw new NotSupportedException();
    }


}
