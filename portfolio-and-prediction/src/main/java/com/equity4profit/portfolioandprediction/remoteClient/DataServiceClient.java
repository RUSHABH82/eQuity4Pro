package com.equity4profit.portfolioandprediction.remoteClient;

import com.equity4profit.portfolioandprediction.exception.PortfolioAndPredictionException;
import com.equity4profit.portfolioandprediction.historydata.CompanyDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "data-Service", url = "${portfolioandprediction.historyServiceEndpoint}")
public interface DataServiceClient {

    @GetMapping("company/{symbol}")
    CompanyDetails getHistorySymbol(@PathVariable String symbol) throws PortfolioAndPredictionException;



}
