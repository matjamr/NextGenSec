package com.sec.gen.next.serviceorchestrator.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum Error implements Serializable {
    UNKOWN_ERROR("Unknown error occured", 0),
    NO_SOURCE_HEADER_INFO("No registration source included in the header", 1),
    INVALID_USER_DATA("Invalid user data, couldnt find one", 2),
    NOT_SUPPORTED_DISPATCHER_METHOD("Not supported dispatcher method", 3),
    INVALID_PLACE_DATA("Invalid place data", 4),
    INVALID_ADDRESS_DATA("Invalid address data", 5),
    INVALID_IMAGE_DATA("Invalid image data", 6),
    NEWS_ERROR("Error during news processing", 7),
    NO_PLACES_ID("There is no palce with given id", 8),
    NO_USER_ID("There is no user with given id", 9),
    UNAUTHORIZED("User is not authorized to enter this page", 10),
    INVALID_PRODUCT_DATA("Invalid product data", 11),
    INVALID_DEVICE_DATA("Invalid device data", 12),
    INVALID_HEADER("Invalid headers provided", 13),
    PRODUCT_EXISTS("Product already exists", 14),
    INVALID_IMAGE_SIZE("Invalid image size", 15),
    INVALID_IMAGE_EXTENSION("Invalid image size", 16),
    INVALID_IMAGE_ID("There is no image with given id", 17),
    PLACE_EXISTS("Place already exists", 18),
    USERS_ALREADY_ADDED("Users already added -> %s", 19),
    INVALID_NOTIFICATION_DATA("Invalid notification data", 20),
    NO_NOTIF_ID("There is no notification with given id", 21),
    INVALID_NEWS("Invalid news data", 22),
    INVALID_HISTORY_ENTRANCE("Invalid history entrance data", 23);

    private String message;
    private Integer code;


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

    public ServiceException getError() {
        return new ServiceException(this);
    }

    public ServiceException getFormattedError(String... args) {
        this.message = this.message.formatted(args);
        return new ServiceException(this);
    }
}
