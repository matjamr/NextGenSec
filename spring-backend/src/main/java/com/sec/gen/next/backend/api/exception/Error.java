package com.sec.gen.next.backend.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum Error implements Serializable {
    UNKOWN_ERROR("Unknown error occured", 0),
    NO_SOURCE_HEADER_INFO("No registration source included in the header", 1),
    INVALID_USER_DATA("Invalid user data, couldnt find one", 2),
    NOT_SUPPORTED_DISPATCHER_METHOD("Not supported dispatcher method", 3),
    INVALID_PLACE_DATA("Invalid place data", 4),
    INVALID_ADDRESS_DATA("Invalid address data", 4),
    ;

    private final String message;
    private final Integer code;


    @Override
    public String toString() {
        return String.format("{\"message\": \"%s\",\"code\": \"%s\"}", message, code);
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
