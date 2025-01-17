package com.equity4profit.liveupdateservice.liveprice.tickertape;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.liveprice.LivePriceResponse;
import com.equity4profit.liveupdateservice.liveprice.LivePriceService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.equity4profit.liveupdateservice.liveprice.tickertape.TickerTapeModels.TickerTapeRequest;
import static com.equity4profit.liveupdateservice.liveprice.tickertape.TickerTapeModels.TickerTapeResponse;

//@Service
public class TickerTapeLivePriceService implements LivePriceService {

    private static final String LIVE_PRICE_TICKER_TAPE_POST_URL = "https://api.tickertape.in/screener/query";
    private static final String[] projects = new String[]{"subindustry", "mrktCapf", "lastPrice"};
    private static final Map<String, String> tickerSidsMap = new HashMap<>();
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders() {{
        add("Accept", "application/json, text/plain, */*");
        add("Content-Type", "application/json");
    }};

    private final RestTemplate restTemplate = new RestTemplate();


    public TickerTapeLivePriceService() throws LiveUpdateException {
        TickerTapeRequest tickerTapeRequest = new TickerTapeRequest();
        TickerTapeResponse tickerTapeResponse = getTickerTapeResponse(tickerTapeRequest);
        tickerTapeResponse.getData().getResults().forEach(results -> {
            String sid = results.getSid();
            String ticker = results.getStock().getInfo().getTicker();
            tickerSidsMap.put(ticker, sid);
        });
    }


    static void addCookie(List<String> rowCookie) {
        if (CollectionUtils.isEmpty(rowCookie)) return;
        HTTP_HEADERS.add("Cookie", StringUtils.join(rowCookie, ';'));
    }

    static void removeCookie() {
        HTTP_HEADERS.remove("Cookie");
    }


    @Override
    public List<LivePriceResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException {
        try {
            TickerTapeRequest tickerTapeRequest = new TickerTapeRequest();
            tickerTapeRequest.setProject(Arrays.asList(projects));
            tickerTapeRequest.setSids(symbols.stream().map(tickerSidsMap::get).filter(s -> !ObjectUtils.isEmpty(s)).toList());
            TickerTapeResponse responseEntity = getTickerTapeResponse(tickerTapeRequest);
            return responseEntity.getData().getResults().parallelStream()
                    .map(TickerTapeModels.Results::getStock)
                    .map(stock -> new LivePriceResponse(stock.getInfo().getTicker(), stock.getAdvancedRatios().getLastPrice()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LiveUpdateException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private TickerTapeResponse getTickerTapeResponse(TickerTapeRequest tickerTapeRequest) throws LiveUpdateException {
        try {
            HttpEntity<TickerTapeRequest> httpEntity = new HttpEntity<>(tickerTapeRequest, HTTP_HEADERS);
            ResponseEntity<TickerTapeResponse> responseEntity = restTemplate.postForEntity(LIVE_PRICE_TICKER_TAPE_POST_URL, httpEntity, TickerTapeResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                addCookie(responseEntity.getHeaders().get(SET_COOKIE_HEADER));
                return responseEntity.getBody();
            }
            throw new LiveUpdateException(responseEntity.getBody().toString(), HttpStatus.valueOf(responseEntity.getStatusCode().value()));
        } catch (Exception e) {
            removeCookie();
            throw new LiveUpdateException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
