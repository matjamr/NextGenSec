package com.sec.next.gen.userservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    INVALID_USER_DATA("Invalid user data", HttpStatus.BAD_REQUEST),
    NO_USER_WITH_EMAIL("There is no user with given email", HttpStatus.BAD_REQUEST),
    USER_EXISTS("User with given email already exists", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    public ServiceError getError() {
        return new ServiceError(this);
    }


}
