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

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean isTokenValid(String token, User user ) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
//                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
