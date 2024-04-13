package com.sec.next.gen.userservice.service.internal.authorization.providers;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.api.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService implements AuthorizationService {

    @Override
    public GoogleAuthorizedUser getUserInfo(String idToken) {

//        String userEmail = extractUsername(idToken);
//        return userRepository.findByEmail(userEmail)
//                .filter(user -> isTokenValid(idToken, user))
//                .map(user -> new GoogleAuthorizedUser()
//                        .email(user.getEmail())
//                        .source(RegistrationSource.JWT)
//                ).orElseThrow(NO_USER_WITH_EMAIL::getError);

        return null;
    }
}
