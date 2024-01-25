package com.sec.gen.next.backend.internal.api.exception;

public class RecoverableServiceException extends RuntimeException {
    private final Error error;

    public RecoverableServiceException() {
        super();
        error = Error.UNKOWN_ERROR;
    }

    public RecoverableServiceException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public RecoverableServiceException(Error error, Throwable cause) {
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
