package com.equity4profit.datahistoryservice.scheduler;

import com.equity4profit.datahistoryservice.config.DataHistoryConfiguration;
import com.equity4profit.datahistoryservice.entity.CompanyCategory;
import com.equity4profit.datahistoryservice.entity.CompanyDetails;
import com.equity4profit.datahistoryservice.entity.HistoryData;
import com.equity4profit.datahistoryservice.exception.DataHistoryServiceException;
import com.equity4profit.datahistoryservice.model.yahoo.YahooHistoryResponseModels;
import com.equity4profit.datahistoryservice.repository.CompanyDetailsRepository;
import com.equity4profit.datahistoryservice.repository.HistoryDataRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.equity4profit.datahistoryservice.model.yahoo.YahooHistoryResponseModels.YahooHistoryResponse;

@Component
@Configuration
@EnableScheduling
public class CompanyDataRefresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDataRefresher.class);
    private static final Long DEFAULT_PERIOD_TO_START = LocalDateTime.of(2021, 1, 1, 9, 15, 0)
            .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    private final DataHistoryConfiguration dataHistoryConfiguration;
    private static final String YAHOO_GET_HISTORY_URL = "https://query1.finance.yahoo.com/v8/finance/chart/{symbol}";
    private final CompanyDetailsRepository companyDetailsRepository;
    private final HistoryDataRepository historyDataRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CompanyDataRefresher(DataHistoryConfiguration dataHistoryConfiguration,
                                CompanyDetailsRepository companyDetailsRepository, HistoryDataRepository historyDataRepository) {
        this.dataHistoryConfiguration = dataHistoryConfiguration;
        this.companyDetailsRepository = companyDetailsRepository;
        this.historyDataRepository = historyDataRepository;
    }

    public static String getYahooHistoryUrl(String symbol, Long from, Long to) {
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString(YAHOO_GET_HISTORY_URL);
        uriComponentsBuilder.queryParam("interval", "1d");
        uriComponentsBuilder.queryParam("events", URLEncoder.encode("capitalGain|Cdiv|split", StandardCharsets.UTF_8));
        uriComponentsBuilder.queryParam("formatted", "false");
        uriComponentsBuilder.queryParam("includeAdjustedClose", "false");
        uriComponentsBuilder.queryParam("period1", from.toString());
        uriComponentsBuilder.queryParam("period2", to.toString());
        return uriComponentsBuilder.buildAndExpand(symbol).toString();
    }

    public List<CompanyDetails> fetchAllLatestCompanyDetails() throws DataHistoryServiceException {
        try {
            ResponseEntity<List<CompanyDetails>> responseEntity = restTemplate.exchange(new RequestEntity<>(HttpMethod.POST, URI.create(dataHistoryConfiguration.getLiveUpdateServiceEndpoint().concat("/details/all"))), new ParameterizedTypeReference<List<CompanyDetails>>() {
            });
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            }
            throw new DataHistoryServiceException(responseEntity.getStatusCode().toString(), HttpStatus.valueOf(responseEntity.getStatusCode().value()));
        } catch (Exception e) {
            if (e instanceof DataHistoryServiceException dataHistoryServiceException) throw dataHistoryServiceException;
            throw new DataHistoryServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Async
    @Scheduled(cron = "0 00 16 * * MON-FRI")
    @Scheduled(fixedRate = Integer.MAX_VALUE)
    @Transactional
    public void updateCompanyDetailsIntoDb() {
        try {
            List<CompanyDetails> companyDetails = new ArrayList<>();
            for (CompanyDetails companyDetail : fetchAllLatestCompanyDetails()) {
                Optional<CompanyDetails> optionalCompanyDetails = companyDetailsRepository.findById(companyDetail.getSymbol());
                optionalCompanyDetails.ifPresentOrElse(c -> {
                    c.setLastPrice(companyDetail.getLastPrice());
                    c.setMarketCapInCr(companyDetail.getMarketCapInCr());
                    c.setIndustry(companyDetail.getIndustry());
                    c.setSector(companyDetail.getSector());
                    c.setName(companyDetail.getName());
                    companyDetails.add(c);
                }, () -> companyDetails.add(companyDetail));
            }
            companyDetailsRepository.saveAll(companyDetails);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Async
    @Scheduled(cron = "0 00 16 * * MON-FRI")
    @Scheduled(fixedRate = Integer.MAX_VALUE)
    public void updateCompanyHistoryPriceIntoDb() {
        for (CompanyDetails companyDetail : companyDetailsRepository.findAllByCategoryIsIn(Arrays.asList(CompanyCategory.S40, CompanyCategory.S250, CompanyCategory.S40NEXT))) {
            try {
                YahooHistoryResponse yahooHistoryResponse = getDataWithNseOrBse(companyDetail).orElseThrow(
                        () -> new DataHistoryServiceException("No history found for symbol: " + companyDetail.getSymbol(), HttpStatus.INTERNAL_SERVER_ERROR));
                Set<HistoryData> historyDataSet = new HashSet<>();
                List<Long> timestamps = yahooHistoryResponse.getChart().getResult().get(0).getTimestamp();
                YahooHistoryResponseModels.Quote quote = yahooHistoryResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0);
                for (int i = 1; i < timestamps.size(); i++) {
                    HistoryData historyData = createHistoryData(timestamps.get(i), quote, i);
                    historyData.setCompanyDetails(companyDetail);
                    historyDataSet.add(historyData);
                }
                historyDataRepository.saveAll(historyDataSet);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


    public Optional<YahooHistoryResponse> getDataWithNseOrBse(CompanyDetails companyDetail) {
        return fetchDataForExchange(companyDetail, ".NS")
                .or(() -> fetchDataForExchange(companyDetail, ".BO"));
    }

    private Optional<YahooHistoryResponse> fetchDataForExchange(CompanyDetails companyDetail, String exchangeSuffix) {
        try {
            Long fromDate = historyDataRepository.getLastRecordedTimestampByCompanyDetails(companyDetail).orElse(DEFAULT_PERIOD_TO_START);
            String url = getYahooHistoryUrl(companyDetail.getSymbol().concat(exchangeSuffix),
                    fromDate, Long.MAX_VALUE);
            ResponseEntity<YahooHistoryResponse> response = restTemplate.getForEntity(url, YahooHistoryResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    private HistoryData createHistoryData(Long timestamp, YahooHistoryResponseModels.Quote quote, int index) {
        return new HistoryData(
                timestamp,
                quote.getOpen().get(index),
                quote.getClose().get(index),
                quote.getHigh().get(index),
                quote.getLow().get(index),
                quote.getVolume().get(index)
        );
    }

}
