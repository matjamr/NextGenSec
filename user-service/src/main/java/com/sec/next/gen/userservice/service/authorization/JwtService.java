package com.sec.next.gen.userservice.service.authorization;

import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.RegistrationSource;
import com.sec.next.gen.userservice.api.User;
import com.sec.next.gen.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.sec.next.gen.userservice.config.Error.INVALID_USER_DATA;

@Service
@RequiredArgsConstructor
public class JwtService implements AuthorizationService {
    private static final String SECRET_KEY = "myverysecuasdadsadasdaasdfawrfQuihfqwhf8q8fdq8whd9pQHPDFHqwudjQNDJqnuiHEUqwuiedjqwnweujhqweuhsdaeqaweaeawearekey";
    private final UserRepository userRepository;
    private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(8);

    @Override
    public GoogleAuthorizedUser getUserInfo(String idToken, RegistrationSource source) {

        String userEmail = extractUsername(idToken);
        return userRepository.findByEmail(userEmail)
                .filter(user -> isTokenValid(idToken, user))
                .map(user -> new GoogleAuthorizedUser()
                        .email(user.getEmail())
                        .source(RegistrationSource.JWT)
                ).orElseThrow(INVALID_USER_DATA::getError);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(GoogleAuthorizedUser authorizedUser) {
        return userRepository.findByEmail(authorizedUser.getEmail())
                .filter(user -> !bcrypt.matches(authorizedUser.getPassword(), user.getPassword()))
                .map(user -> generateToken(Map.of(), authorizedUser))
                .orElseThrow(INVALID_USER_DATA::getError);
    }

    public String generateToken(Map<String, Object> extraClaims, GoogleAuthorizedUser authorizedUser) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(authorizedUser.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
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
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
