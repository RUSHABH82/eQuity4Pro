package com.equity4profit.liveupdateservice.livedetail.tradingview;

import com.equity4profit.liveupdateservice.exception.LiveUpdateException;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailService;
import com.equity4profit.liveupdateservice.livedetail.LiveDetailsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.equity4profit.liveupdateservice.livedetail.tradingview.TradingViewModels.*;

@Service
public class TradingViewLiveDetailService implements LiveDetailService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String TRADING_VIEW_URL = "https://scanner.tradingview.com/india/scan?label-product=markets-screener";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<LiveDetailsResponse> getLivePriceResponses(List<String> symbols) throws LiveUpdateException {
        return getAllLivePriceResponses().stream().filter(liveDetailsResponse -> symbols.contains(liveDetailsResponse.getSymbol())).collect(Collectors.toList());
    }

    @Override
    public List<LiveDetailsResponse> getAllLivePriceResponses() throws LiveUpdateException {
        try {
            return fetchAllTradingViewLiveDetailsFromAPI();
        } catch (Exception e) {
            throw new LiveUpdateException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private List<LiveDetailsResponse> fetchAllTradingViewLiveDetailsFromAPI() throws JsonProcessingException {
        List<LiveDetailsResponse> liveDetailsResponses = new ArrayList<>();
        DataColumns[] dataColumns = DataColumns.values();
        List<String> columns = new ArrayList<>(dataColumns.length);
        for (int i = 0; i < dataColumns.length; i++) {
            columns.add(i, dataColumns[i].getValue());
        }
        MarketDataRequest request = new MarketDataRequest();
        request.setColumns(columns);
        request.setOptions(new MarketDataRequest.Options("en"));
        request.setSort(new MarketDataRequest.Sort(DataColumns.COLUMN_MARKET_CAP_BASIC, "desc", false));
        request.setIgnore_unknown_fields(false);
        request.setRange(Arrays.asList(0, Integer.MAX_VALUE));
        request.setPreset("all_stocks");

        HttpEntity<String> requestEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(request));
        ResponseEntity<MarketDataFetchResponse> responseEntity = restTemplate.postForEntity(TRADING_VIEW_URL, requestEntity, MarketDataFetchResponse.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(responseEntity.getStatusCode().toString());
        }
        MarketDataFetchResponse marketDataFetchResponse = responseEntity.getBody();
        for (StockData stockData : marketDataFetchResponse.getData()) {
            LiveDetailsResponse entity = new LiveDetailsResponse();
            setKeyValueDataForEntity(entity, dataColumns, stockData.getD());
            liveDetailsResponses.add(entity);
        }
        return liveDetailsResponses;
    }


    private void setKeyValueDataForEntity(LiveDetailsResponse entity, DataColumns[] dataColumns, List<Object> values) {
        if (dataColumns.length != values.size()) {
            throw new IllegalArgumentException("dataColumns and values must have the same length.");
        }
        Map<String, Object> stringObjectMap = new HashMap<>();
        for (int i = 0; i < dataColumns.length; i++) {
            DataColumns dataColumn = dataColumns[i];
            Object value = values.get(i);
            if (!ObjectUtils.isEmpty(value)) {
                stringObjectMap.put(dataColumn.getValue(), value);
            }
        }
        entity.setSymbol(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.name.getValue(), ""), DataColumns.ColumnType.STRING_TYPE_REFERENCE));
        entity.setName(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.description.getValue(), ""), DataColumns.ColumnType.STRING_TYPE_REFERENCE));
        entity.setSector(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.sector.getValue(), ""), DataColumns.ColumnType.STRING_TYPE_REFERENCE));
        entity.setIndustry(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.industry.getValue(), ""), DataColumns.ColumnType.STRING_TYPE_REFERENCE));
        entity.setLastPrice(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.close.getValue(), 0), DataColumns.ColumnType.DOUBLE_TYPE_REFERENCE));
        entity.setMarketCapInCr(OBJECT_MAPPER.convertValue(stringObjectMap.getOrDefault(DataColumns.market_cap_basic.getValue(), 0), DataColumns.ColumnType.DOUBLE_TYPE_REFERENCE) / 10000000);
    }

}
