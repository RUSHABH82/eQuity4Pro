package com.equity4profit.datahistoryservice.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class DataHistoryServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public DataHistoryServiceException() {
        super();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }

    public DataHistoryServiceException(String errorMessage, HttpStatus hTTPStatusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        httpStatus = hTTPStatusCode;
    }


    public DataHistoryServiceException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
        errorMessage = httpStatus.toString();
    }


    public DataHistoryServiceException(String msg, Throwable cause) {
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
