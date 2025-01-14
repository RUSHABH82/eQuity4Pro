package com.equity4profit.liveupdateservice;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailService;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailsResponse;
import com.equity4profit.liveupdateservice.livedetail.tickertape.TickerTapeLiveDetailService;
import com.equity4profit.liveupdateservice.livedetail.tradingview.TradingViewLiveDetailService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LiveDetailMergeService implements LiveDetailService {

    private final TickerTapeLiveDetailService tickerTapeLiveDetailService;
    private final TradingViewLiveDetailService tradingViewLiveDetailService;

    public LiveDetailMergeService(TickerTapeLiveDetailService tickerTapeLiveDetailService,
                                  TradingViewLiveDetailService tradingViewLiveDetailService) {
        this.tickerTapeLiveDetailService = tickerTapeLiveDetailService;
        this.tradingViewLiveDetailService = tradingViewLiveDetailService;
    }


    @Override
    public List<LiveDetailsResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException {
        List<LiveDetailsResponse> tickerTapeLivePriceResponses = tickerTapeLiveDetailService.getLivePriceResponses(symbols);
        if (symbols.size() == tickerTapeLivePriceResponses.size()) {
            return tickerTapeLivePriceResponses;
        }
        symbols.removeAll(tickerTapeLivePriceResponses.stream().map(LiveDetailsResponse::getSymbol).toList());
        List<LiveDetailsResponse> tradingViewLivePriceResponses = tradingViewLiveDetailService.getLivePriceResponses(symbols);
        tickerTapeLivePriceResponses.addAll(tradingViewLivePriceResponses);
        return tickerTapeLivePriceResponses;
    }

    @Override
    public List<LiveDetailsResponse> getAllLivePriceResponses() throws LiveUpdateException {
        List<LiveDetailsResponse> tickerTapeLivePriceResponses = tickerTapeLiveDetailService.getAllLivePriceResponses();
        List<LiveDetailsResponse> tradingViewLivePriceResponses = tradingViewLiveDetailService.getAllLivePriceResponses();

        Set<String> tickerTapeLivePriceSymbols = tickerTapeLivePriceResponses.stream().map(LiveDetailsResponse::getSymbol).collect(Collectors.toSet());
        tradingViewLivePriceResponses.stream()
                .filter(liveDetailsResponse -> !tickerTapeLivePriceSymbols.contains(liveDetailsResponse.getSymbol()))
                .forEach(tickerTapeLivePriceResponses::add);
        return tickerTapeLivePriceResponses;

    }
}
