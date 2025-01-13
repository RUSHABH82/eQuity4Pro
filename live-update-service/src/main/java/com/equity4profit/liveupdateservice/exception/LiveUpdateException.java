package com.equity4profit.liveupdateservice.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class LiveUpdateException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public LiveUpdateException() {
        super();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }

    public LiveUpdateException(String errorMessage, HttpStatus hTTPStatusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        httpStatus = hTTPStatusCode;
    }


    public LiveUpdateException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
        errorMessage = httpStatus.toString();
    }


    public LiveUpdateException(String msg, Throwable cause) {
        super(msg, cause);
        this.errorMessage = msg;
        this.httpStatus = null;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
