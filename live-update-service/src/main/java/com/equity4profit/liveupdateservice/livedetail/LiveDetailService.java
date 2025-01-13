package com.equity4profit.liveupdateservice.livedetail;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;

import java.util.List;

public interface LiveDetailService {

    List<LiveDetailsResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException;

    List<LiveDetailsResponse> getAllLivePriceResponses() throws LiveUpdateException;
}
