package com.sec.next.gen.userservice.controller;

import com.sec.next.gen.userservice.config.Error;
import com.sec.next.gen.userservice.config.ServiceError;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ResponseBody
    @ExceptionHandler(ServiceError.class)
    public ResponseEntity<Map<String, String>> errorHandler(final ServiceError serviceError) {

        final Error error = serviceError.getError();
        log.error(error.getMessage());

        return ResponseEntity
                .status(error.getHttpStatus().value())
                .body(Map.of(
                        "message", error.getMessage()
                ));
    }

    @ResponseBody
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> errorHandler(final FeignException error) {
        log.error(error.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", error.getMessage()
                ));
    }

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> errorHandler(final ExpiredJwtException expiredJwtException) {
        log.error(expiredJwtException.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message", "Expired JWT token"
                ));
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> errorHandler(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", exception.getMessage()
                ));
    }
}
