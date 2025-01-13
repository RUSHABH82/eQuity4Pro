package com.equity2profit.datahistoryservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "resultStatus"
})
public class ResultStatusResponse {
    @JsonProperty("resultStatus")
    private final ResultStatus resultStatus;

    public ResultStatusResponse(ResultStatus resultStatus) {
        super();
        this.resultStatus = resultStatus;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }


}
