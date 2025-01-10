package com.equity4profit.liveupdateservice.liveprice.groww;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.liveprice.LivePriceResponse;
import com.equity4profit.liveupdateservice.liveprice.LivePriceService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.equity4profit.liveupdateservice.liveprice.groww.GrowwModels.ExchangeAggReqMap;
import static com.equity4profit.liveupdateservice.liveprice.groww.GrowwModels.ExchangeAggResp;

@RestController
@RequestMapping("groww")
public class GrowLivePriceService implements LivePriceService {
    private static final String NSE_SYMBOL = "NSE";
    private static final String BSE_SYMBOL = "BSE";
    private static final int GET_RESULT_LIMIT = 40;
    private static final String LIVE_PRICE_GROWW_POST_URL = "https://groww.in/v1/api/stocks_data/v1/tr_live_delayed/segment/CASH/latest_aggregated";
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders() {{
        add("Accept", "application/json, text/plain, */*");
        add("Content-Type", "application/json");
    }};
    private final RestTemplate restTemplate = new RestTemplate();
    ExecutorService executor = Executors.newFixedThreadPool(8);

    static void addCookie(List<String> rowCookie) {
        if (CollectionUtils.isEmpty(rowCookie)) return;
        HTTP_HEADERS.add("Cookie", StringUtils.join(rowCookie, ';'));
    }

    static void removeCookie() {
        HTTP_HEADERS.remove("Cookie");
    }

    @Override
    @PostMapping("live")
    public List<LivePriceResponse> getLivePriceResponses(@RequestBody List<String> symbols) throws LiveUpdateException {
        try {
            List<LivePriceResponse> livePriceResponses = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < symbols.size(); i = i + GET_RESULT_LIMIT) {
                int finalI = i;
                Runnable runnable = () -> getLivePrice(livePriceResponses, buildSubRequest(symbols, finalI));
                Thread thread = new Thread(runnable);
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                thread.join();
            }
            return livePriceResponses;
        } catch (Exception e) {
            removeCookie();
            throw new LiveUpdateException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ExchangeAggReqMap buildSubRequest(List<String> symbols, int finalI) {
        int end = Math.min(finalI + GET_RESULT_LIMIT, symbols.size());
        ExchangeAggReqMap.ExchangeInfo nseExchangeInfo = new ExchangeAggReqMap.ExchangeInfo();
        nseExchangeInfo.setPriceSymbolList(symbols.subList(finalI, end));
        ExchangeAggReqMap exchangeAggReqMap = new ExchangeAggReqMap();
        exchangeAggReqMap.setExchangeAggReqMap(Map.of(NSE_SYMBOL, nseExchangeInfo, BSE_SYMBOL, new ExchangeAggReqMap.ExchangeInfo()));
        return exchangeAggReqMap;
    }


    private void getLivePrice(List<LivePriceResponse> livePriceResponses, ExchangeAggReqMap exchangeAggReqMap) {
        HttpEntity<ExchangeAggReqMap> httpEntity = new HttpEntity<>(exchangeAggReqMap, HTTP_HEADERS);
        ResponseEntity<ExchangeAggResp> responseEntity = restTemplate.postForEntity(LIVE_PRICE_GROWW_POST_URL, httpEntity, ExchangeAggResp.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            addCookie(responseEntity.getHeaders().get("Set-Cookie"));
            responseEntity.getBody().getExchangeAggRespMap().forEach((s, exchangeInfo) -> {
                exchangeInfo.getPriceLivePointsMap().forEach((s1, growPriceLivePoint) -> livePriceResponses.add(new LivePriceResponse(growPriceLivePoint.symbol, growPriceLivePoint.ltp)));
            });
        }
    }

}
