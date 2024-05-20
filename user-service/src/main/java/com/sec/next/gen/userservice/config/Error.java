package com.sec.next.gen.userservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    INVALID_USER_DATA("Invalid user data", HttpStatus.BAD_REQUEST),
    NO_USER_WITH_EMAIL("There is no user with given email", HttpStatus.BAD_REQUEST),
    PASSWORD_DO_NOT_MATCH("Passwords do not match", HttpStatus.BAD_REQUEST),
    CLIENT_NOT_SUPPORTED("Client with given context is not supported", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("Expired token", HttpStatus.UNAUTHORIZED),
    USER_EXISTS("User with given email already exists", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus httpStatus;

    public ServiceError getError() {
        return new ServiceError(this);
    }


}
