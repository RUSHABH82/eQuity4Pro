package com.equity2profit.datahistoryservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "errorMessage",
        "errorCode"
})
public class ResultStatus {
    @JsonProperty("status")
    private String status;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("errorCode")
    private String errorCode;


    public ResultStatus(String status, String errorMessage, String errorCode) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
