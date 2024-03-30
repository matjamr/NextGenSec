package com.sec.next.gen.userservice.service;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.Source;
import com.sec.next.gen.userservice.models.User;
import com.sec.next.gen.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService implements AuthorizationService {
    private static final String SECRET_KEY = "myverysecuasdadsadasdaasdfawrfQuihfqwhf8q8fdq8whd9pQHPDFHqwudjQNDJqnuiHEUqwuiedjqwnweujhqweuhsdaeqaweaeawearekey";
    private final UserRepository userRepository;
    private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(8);

    @Override
    public Mono<AuthorizedUser> getUserInfo(String idToken, Source source) {

        String userEmail = extractUsername(idToken);
        if(isTokenValid(idToken, userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Invalid user")))) {
            return Mono.just(new AuthorizedUser().setEmail(userEmail).setSource(Source.JWT.name()));
        };

        throw new RuntimeException("Invalid user");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(AuthorizedUser authorizedUser) {
        User user = userRepository.findByEmail(authorizedUser.getEmail()).orElseThrow(() -> new RuntimeException("Invalid user"));

        if(!bcrypt.matches(authorizedUser.getPassword(), user.password())) {
            throw new RuntimeException("Invalid password");
        }
        return generateToken(new HashMap<>(), authorizedUser);
    }

    public String generateToken(Map<String, Object> extraClaims, AuthorizedUser authorizedUser) {
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
        return (username.equals(user.email())) && !isTokenExpired(token);
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
