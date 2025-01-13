package com.equity4profit.liveupdateservice.liveprice;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;

import java.util.List;

public interface LivePriceService {
    List<LivePriceResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException;

}
