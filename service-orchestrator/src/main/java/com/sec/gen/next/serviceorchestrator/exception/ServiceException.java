package com.sec.gen.next.serviceorchestrator.exception;


public class ServiceException extends RuntimeException {
    private Error error;
    private String message;

    public ServiceException() {
        super();
        error = Error.UNKOWN_ERROR;
    }

    public ServiceException(Error error) {
        super(error.getMessage());
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
