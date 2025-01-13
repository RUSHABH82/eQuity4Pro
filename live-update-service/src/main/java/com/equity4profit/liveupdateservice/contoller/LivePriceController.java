package com.equity4profit.liveupdateservice.contoller;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.liveprice.LivePriceResponse;
import com.equity4profit.liveupdateservice.liveprice.route.LivePriceRoute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("price")
public class LivePriceController {

    private final LivePriceRoute livePriceRoute;

    public LivePriceController(LivePriceRoute livePriceRoute) {
        this.livePriceRoute = livePriceRoute;
    }

    @PostMapping
    public List<LivePriceResponse> getLivePriceResponses(@RequestBody List<String> symbols) throws LiveUpdateException {
        return livePriceRoute.getLivePriceResponses(symbols);
    }
}
