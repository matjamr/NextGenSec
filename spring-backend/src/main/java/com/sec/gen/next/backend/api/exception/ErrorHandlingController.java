package com.sec.gen.next.backend.api.exception;

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
    Error employeeNotFoundHandler(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final ServiceException ex) {
        ex.printStackTrace();

        return ex.getError();
    }
}
