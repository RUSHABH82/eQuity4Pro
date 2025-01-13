package com.equity4profit.liveupdateservice.liveprice.zerodha;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.liveprice.LivePriceResponse;
import com.equity4profit.liveupdateservice.liveprice.LivePriceService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZerodhaLivePriceService implements LivePriceService {

    private static final String LIVE_PRICE_ZERODHA_GET_URL = "https://quotes-api.tickertape.in/quotes";
    private static final String LIVE_PRICE_ZERODHA_NIFTY500 = "https://api.tickertape.in/indices/constituents/.NIFTY500";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String SIDS = "sids";
    private static final int GET_RESULT_LIMIT = 33;
    private static final Set<String> NIFTY500_SYMBOLS = HashSet.newHashSet(500);

    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders() {{
        add("Accept", "application/json, text/plain, */*");
        add("Content-Type", "application/json");
    }};
    private final RestTemplate restTemplate = new RestTemplate();


    static void addCookie(List<String> rowCookie) {
        if (CollectionUtils.isEmpty(rowCookie)) return;
        HTTP_HEADERS.add("Cookie", StringUtils.join(rowCookie, ';'));
    }

    static void removeCookie() {
        HTTP_HEADERS.remove("Cookie");
    }

    public static Set<String> getNifty500Symbols() {
        if (NIFTY500_SYMBOLS.isEmpty()) {
            String url = UriComponentsBuilder.fromUriString(LIVE_PRICE_ZERODHA_NIFTY500)
                    .toUriString();
            ResponseEntity<ZerodhaModels.ZerodhaConstituentsResponse> responseEntity =
                    new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<>(HTTP_HEADERS), ZerodhaModels.ZerodhaConstituentsResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                addCookie(responseEntity.getHeaders().get(SET_COOKIE_HEADER));
                responseEntity.getBody().getData().getConstituents().forEach(data -> NIFTY500_SYMBOLS.add(data.getSid()));
            }
        }
        return Collections.unmodifiableSet(NIFTY500_SYMBOLS);

    }

    @Override
    public List<LivePriceResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException {
        try {
            Set<LivePriceResponse> livePriceResponses = new HashSet<>();
            getLivePrice(livePriceResponses, symbols);
            return livePriceResponses.stream().toList();
        } catch (Exception e) {
            removeCookie();
            throw new LiveUpdateException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void getLivePrice(Set<LivePriceResponse> livePriceResponses, List<String> symbols) {
        String url = UriComponentsBuilder.fromUriString(LIVE_PRICE_ZERODHA_NIFTY500)
                .toUriString();
        ResponseEntity<ZerodhaModels.ZerodhaConstituentsResponse> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<>(HTTP_HEADERS), ZerodhaModels.ZerodhaConstituentsResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            addCookie(responseEntity.getHeaders().get(SET_COOKIE_HEADER));
            responseEntity.getBody().getData().getConstituents().forEach(
                    data -> {
                        livePriceResponses.add(new LivePriceResponse(data.getSid(), data.getLastPrice()));
                    }
            );
        }
    }

}
