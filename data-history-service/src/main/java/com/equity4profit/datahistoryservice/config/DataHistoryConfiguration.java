package com.equity4profit.datahistoryservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "datahistoryconfiguration")
public class DataHistoryConfiguration {


    private String liveUpdateServiceEndpoint;

    public String getLiveUpdateServiceEndpoint() {
        return liveUpdateServiceEndpoint;
    }

    public void setLiveUpdateServiceEndpoint(String liveUpdateServiceEndpoint) {
        this.liveUpdateServiceEndpoint = liveUpdateServiceEndpoint;
    }
}
