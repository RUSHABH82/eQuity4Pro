package com.equity4profit.portfolioandprediction.service;

import com.equity4profit.portfolioandprediction.config.CompanyCategoryConfig;
import com.equity4profit.portfolioandprediction.entity.StockScanYield;
import com.equity4profit.portfolioandprediction.exception.PortfolioAndPredictionException;
import com.equity4profit.portfolioandprediction.historydata.CompanyCategory;
import com.equity4profit.portfolioandprediction.historydata.CompanyDetails;
import com.equity4profit.portfolioandprediction.historydata.HistoryData;
import com.equity4profit.portfolioandprediction.model.liveprice.LivePriceModels;
import com.equity4profit.portfolioandprediction.remoteClient.DataServiceClient;
import com.equity4profit.portfolioandprediction.remoteClient.LiveUpdateServiceClient;
import com.equity4profit.portfolioandprediction.repository.StockScanYieldRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Configuration
@EnableScheduling
@RestController
public class HighLowScanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HighLowScanService.class);
    private final DataServiceClient dataServiceClient;
    private final LiveUpdateServiceClient liveUpdateServiceClient;
    private final CompanyCategoryConfig companyCategoryConfig;
    private final StockScanYieldRepository stockScanYieldRepository;

    public HighLowScanService(DataServiceClient dataServiceClient, LiveUpdateServiceClient liveUpdateServiceClient, CompanyCategoryConfig companyCategoryConfig, StockScanYieldRepository stockScanYieldRepository) {
        this.dataServiceClient = dataServiceClient;
        this.liveUpdateServiceClient = liveUpdateServiceClient;
        this.companyCategoryConfig = companyCategoryConfig;
        this.stockScanYieldRepository = stockScanYieldRepository;
    }

    //@Scheduled(fixedRate = Integer.MAX_VALUE)
    @GetMapping("results")
    public List<HighLowScanServiceModels.ScanCompanyResults> fetchStrategyFromDb() throws PortfolioAndPredictionException {
        List<StockScanYield> stockScanYieldList = stockScanYieldRepository.findAll();
        Map<String, Double> livePriceMap = getLivePriceMap(stockScanYieldList);
        Map<String, List<StockScanYield>> categoryMap = groupByCategory(stockScanYieldList);
        return generateScanCompanyResults(categoryMap, livePriceMap);
    }

    private Map<String, Double> getLivePriceMap(List<StockScanYield> stockScanYieldList) throws PortfolioAndPredictionException {
        Set<String> symbols = stockScanYieldList.stream().map(StockScanYield::getSymbol).collect(Collectors.toSet());
        List<LivePriceModels.LivePriceResponse> livePriceResponses = liveUpdateServiceClient.getLivePriceResponses(symbols);
        return livePriceResponses.stream().collect(Collectors.toMap(LivePriceModels.LivePriceResponse::getSymbol, LivePriceModels.LivePriceResponse::getLtp));
    }

    private Map<String, List<StockScanYield>> groupByCategory(List<StockScanYield> stockScanYieldList) {
        return stockScanYieldList.stream().collect(Collectors.groupingBy(stockScanYield -> stockScanYield.getCompanyCategory().name()));
    }

    private List<HighLowScanServiceModels.ScanCompanyResults> generateScanCompanyResults(Map<String, List<StockScanYield>> categoryMap, Map<String, Double> livePriceMap) {
        Set<String> companyCategory = companyCategoryConfig.getCategories().keySet();
        return companyCategory.stream().map(category -> {
            HighLowScanServiceModels.ScanCompanyResults companyResults = new HighLowScanServiceModels.ScanCompanyResults();
            companyResults.setCategory(category);
            List<HighLowScanServiceModels.FilteredCompanies> filteredCompaniesList = generateFilteredCompanies(categoryMap.get(category), livePriceMap);
            companyResults.setFilteredCompanies(filteredCompaniesList);
            return companyResults;
        }).collect(Collectors.toList());
    }

    private List<HighLowScanServiceModels.FilteredCompanies> generateFilteredCompanies(List<StockScanYield> stockScanYieldList, Map<String, Double> livePriceMap) {
        Map<String, List<StockScanYield>> symbolToScanYieldMap = stockScanYieldList.stream().collect(Collectors.groupingBy(StockScanYield::getSymbol));
        return symbolToScanYieldMap.entrySet().stream().map(entry -> createFilteredCompanies(entry.getKey(), entry.getValue(), livePriceMap)).collect(Collectors.toList());
    }

    private HighLowScanServiceModels.FilteredCompanies createFilteredCompanies(String companySymbol, List<StockScanYield> stockScanYields, Map<String, Double> livePriceMap) {
        HighLowScanServiceModels.FilteredCompanies filteredCompanies = new HighLowScanServiceModels.FilteredCompanies();
        filteredCompanies.setSymbol(companySymbol);
        filteredCompanies.setCurrentPrice(livePriceMap.getOrDefault(companySymbol, 0.0));


        List<HighLowScanServiceModels.FilteredEvents> filteredEvents = stockScanYields.stream().sorted(Comparator.comparing(StockScanYield::getScannedTimeStamp).reversed()).limit(5).map(stockScanYield -> {
            HighLowScanServiceModels.FilteredEvents filteredEvent = createFilteredEvents(stockScanYield);
            filteredEvent.setOvervaluation(getOvervaluationPercentage(filteredCompanies.getCurrentPrice(), filteredEvent.getBuy()));
            return filteredEvent;
        }).collect(Collectors.toList());

        filteredCompanies.setFilteredEvents(filteredEvents);
        return filteredCompanies;
    }

    private HighLowScanServiceModels.FilteredEvents createFilteredEvents(StockScanYield stockScanYield) {
        HighLowScanServiceModels.FilteredEvents event = new HighLowScanServiceModels.FilteredEvents();
        event.setBuy(stockScanYield.getBuy());
        event.setSell(stockScanYield.getSell());
        event.setVariation(stockScanYield.getVariation());
        event.setDate(Instant.ofEpochSecond(stockScanYield.getScannedTimeStamp()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        return event;
    }


    @Async
    @Scheduled(cron = "0 00 16 * * MON-FRI")
    @Scheduled(fixedRate = Integer.MAX_VALUE)
    @Transactional
    public void applyStrategy() {
        List<StockScanYield> stockScanYields = new ArrayList<>();
        Map<String, Set<String>> categoryConfigCategories = companyCategoryConfig.getCategories();
        for (Map.Entry<String, Set<String>> entry : categoryConfigCategories.entrySet()) {
            for (String symbol : entry.getValue()) {
                stockScanYields.addAll(getYieldResponse(symbol, CompanyCategory.valueOf(entry.getKey())));
            }
        }
        stockScanYieldRepository.saveAll(stockScanYields);
    }


    public List<StockScanYield> getYieldResponse(String symbol, CompanyCategory companyCategory) {
        List<StockScanYield> stockScanYields = new ArrayList<>();
        try {
            CompanyDetails companyDetails = dataServiceClient.getHistorySymbol(symbol);
            List<HistoryData> historyData = companyDetails.getHistoryData().stream().toList();

            stockScanYieldRepository.deleteAllBySymbol(symbol);
            for (int i = 0; i < historyData.size() - 3; i++) {
                getVariation(historyData, i, stockScanYields);
            }
            stockScanYields.forEach(ssy -> {
                ssy.setSymbol(symbol);
                ssy.setCompanyCategory(companyCategory);
            });
            stockScanYields.sort((o1, o2) -> -o1.getScannedTimeStamp().compareTo(o2.getScannedTimeStamp()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return stockScanYields;
    }

    private void getVariation(List<HistoryData> dailyStatuses, int currentDay, List<StockScanYield> stockScanYields) {
        double maxHigh = Double.NEGATIVE_INFINITY;
        double minLow = Double.POSITIVE_INFINITY;
        for (int j = currentDay; j < currentDay + 3; j++) {
            double high = dailyStatuses.get(j).getHigh();
            double low = dailyStatuses.get(j).getLow();
            double open = dailyStatuses.get(j).getOpen();
            double close = dailyStatuses.get(j).getClose();
            if (open > close) continue; // Ignore the negative closing.
            maxHigh = Math.max(maxHigh, high);
            minLow = Math.min(minLow, low);
        }
        double threeDaysGrowthInPercentage = getPercentageChange(minLow, maxHigh);
        if (threeDaysGrowthInPercentage < 20 && threeDaysGrowthInPercentage > 15) {
            StockScanYield stockScanYield = new StockScanYield();
            stockScanYield.setVariation(threeDaysGrowthInPercentage);
            stockScanYield.setSell(maxHigh);
            stockScanYield.setBuy(minLow);
            stockScanYield.setScannedTimeStamp(dailyStatuses.get(currentDay).getTimestamp());
            stockScanYields.add(stockScanYield);
        }
    }


    public static double getPercentageChange(double from, double to) {
        return ((to - from) / from) * 100;
    }

    public static double getOvervaluationPercentage(double current, double target) {
        return ((current - target) / target) * 100;
    }
}
