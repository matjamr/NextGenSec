package com.sec.gen.next.serviceorchestrator.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;



@ControllerAdvice
public class ErrorHandlingController {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Error serviceExceptionHandler(final ServiceException ex) {
        ex.printStackTrace();

        return ex.getError();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Error exceptionHandler(final Exception ex) {
        ex.printStackTrace();

        return Error.INTERNAL_SERVER_ERROR.withFormattedError(ex.getMessage());
    }

}
