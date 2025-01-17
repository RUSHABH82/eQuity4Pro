package com.equity4profit.portfolioandprediction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "portfolioandprediction")
public class PortfolioAndPredictionConfiguration {

    private String historyServiceEndpoint;

    public String getHistoryServiceEndpoint() {
        return historyServiceEndpoint;
    }

    public void setHistoryServiceEndpoint(String historyServiceEndpoint) {
        this.historyServiceEndpoint = historyServiceEndpoint;
    }
}
