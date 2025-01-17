package com.equity4profit.portfolioandprediction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "equity4profit")
public class CompanyCategoryConfig {

    private Map<String, Set<String>> categories;

    public Map<String, Set<String>> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Set<String>> categories) {
        this.categories = categories;
    }
}
