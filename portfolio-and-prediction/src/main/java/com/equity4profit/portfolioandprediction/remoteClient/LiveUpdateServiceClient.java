package com.equity4profit.portfolioandprediction.remoteClient;

import com.equity4profit.portfolioandprediction.exception.PortfolioAndPredictionException;
import com.equity4profit.portfolioandprediction.model.liveprice.LivePriceModels;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "live-price-Service", url = "${portfolioandprediction.livepriceServiceEndpoint}")
public interface LiveUpdateServiceClient {

    @PostMapping("price")
    List<LivePriceModels.LivePriceResponse> getLivePriceResponses(@RequestBody Collection<String> symbols) throws PortfolioAndPredictionException;


}
