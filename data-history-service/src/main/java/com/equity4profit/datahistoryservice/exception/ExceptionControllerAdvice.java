package com.equity4profit.datahistoryservice.exception;


import com.equity4profit.datahistoryservice.model.ResultStatus;
import com.equity4profit.datahistoryservice.model.ResultStatusResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String FAILED_MESSAGE = "FAILED";
    private static final String TECHNICAL_DIFFICULTY_MESSAGE = "Technical Difficulty!";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultStatusResponse> handleException(Exception ex) {
        ResultStatus status = new ResultStatus(FAILED_MESSAGE, ex.getMessage(), TECHNICAL_DIFFICULTY_MESSAGE);
        return new ResponseEntity<>(new ResultStatusResponse(status), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(DataHistoryServiceException.class)
    public ResponseEntity<Object> handleRestException(DataHistoryServiceException ex) {
        ResultStatus status = new ResultStatus(
                FAILED_MESSAGE, Integer.toString(ex.getHttpStatus().value()), ex.getErrorMessage());
        return new ResponseEntity<>(new ResultStatusResponse(status), ex.getHttpStatus());
    }
}
