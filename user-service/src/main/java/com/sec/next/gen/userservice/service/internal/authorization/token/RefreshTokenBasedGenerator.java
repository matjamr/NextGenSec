package com.sec.next.gen.userservice.service.internal.authorization.token;

import com.next.gen.api.User;
import com.next.gen.sec.model.GoogleAuthorizedUser;
import com.next.gen.sec.model.Token;
import com.sec.next.gen.userservice.repository.UserRepository;
import com.sec.next.gen.userservice.service.external.redis.RedisService;
import com.sec.next.gen.userservice.service.internal.authorization.token.generator.TokenCreator;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.sec.next.gen.userservice.config.Error.INVALID_USER_DATA;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class RefreshTokenBasedGenerator implements Function<TokenContext, Token> {

    private final UserRepository userRepository;
    private final TokenCreator accessTokenGenerator;
    private final TokenDecoder tokenDecoder;
    private final RedisService redisService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Token apply(TokenContext tokenContext) {
        var actualToken = tokenContext.getToken().replaceFirst("Bearer ", "");

        User user = userRepository.findByEmail(tokenDecoder.extractUsername(actualToken))
                .orElseThrow(INVALID_USER_DATA::getError);

        String storedRefreshToken = redisService.getRefreshToken(user.getEmail());

        Map<String, String> newHeaders = new HashMap<>();
        newHeaders.put("source", getHeader("source"));
        newHeaders.put("user-agent", getHeader("user-agent"));
        newHeaders.put("origin", getHeader("origin"));
        newHeaders.put("sec-ch-ua-mobile", getHeader("sec-ch-ua-mobile"));

        var headers = tokenDecoder.extractAllHeaders(storedRefreshToken);
        compareHeaders(headers, newHeaders);

        String accessToken = accessTokenGenerator.createToken(
                new TokenParams(new GoogleAuthorizedUser().email(user.getEmail()), 86400000L));

        return new Token().accessToken(accessToken).refreshToken(actualToken);
    }

    private void compareHeaders(Header<?> headers, Map<String, String> newHeaders) {
        newHeaders.forEach((key, value) -> {
            if(isNull(value)) return;
            if (!headers.containsKey(key) || !headers.get(key).equals(value)) {
                throw INVALID_USER_DATA.getError();
            }
        });
    }

    private String getHeader(String headerName) {
        return httpServletRequest.getHeader(headerName);
    }

}
