package com.sec.gen.next.backend.internal.api.exception;


public class ServiceException extends RuntimeException {
    private final Error error;

    public ServiceException() {
        super();
        error = Error.UNKOWN_ERROR;
    }

    public ServiceException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ServiceException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
