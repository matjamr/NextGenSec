package com.sec.gen.next.serviceorchestrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Error serviceExceptionHandler(final ServiceException ex) {
        ex.printStackTrace();

        return ex.getError();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Error exceptionHandler(final Exception ex) {
        ex.printStackTrace();

        return Error.INTERNAL_SERVER_ERROR.withFormattedError(ex.getMessage());
    }

}
