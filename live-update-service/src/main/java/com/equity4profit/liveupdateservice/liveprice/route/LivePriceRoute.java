package com.equity4profit.liveupdateservice.liveprice.route;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.liveprice.LivePriceResponse;
import com.equity4profit.liveupdateservice.liveprice.LivePriceService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LivePriceRoute implements LivePriceService {

    private final List<LivePriceService> livePriceServices;
    private final AtomicInteger counter = new AtomicInteger(0);

    public LivePriceRoute(List<LivePriceService> livePriceServices) {
        this.livePriceServices = livePriceServices;
    }

    @Override
    public List<LivePriceResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException {
        return livePriceServices.get(getNext()).getLivePriceResponses(symbols);

    }

    public int getNext() {
        return counter.getAndUpdate(value -> value < livePriceServices.size() - 1 ? value + 1 : 0);
    }
}
