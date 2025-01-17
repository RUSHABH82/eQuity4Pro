package com.equity4profit.portfolioandprediction.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class HighLowScanServiceModels {


    public static class ScanCompanyResults {
        @JsonProperty("category")
        private String category;
        @JsonProperty("filteredCompanies")
        private List<FilteredCompanies> filteredCompanies;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<FilteredCompanies> getFilteredCompanies() {
            return filteredCompanies;
        }

        public void setFilteredCompanies(List<FilteredCompanies> filteredCompanies) {
            this.filteredCompanies = filteredCompanies;
        }
    }

    public static class FilteredCompanies {
        @JsonProperty("symbol")
        private String symbol;

        @JsonProperty("currentPrice")
        private double currentPrice;

        @JsonProperty("filteredEvents")
        private List<FilteredEvents> filteredEvents;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public List<FilteredEvents> getFilteredEvents() {
            return filteredEvents;
        }

        public void setFilteredEvents(List<FilteredEvents> filteredEvents) {
            this.filteredEvents = filteredEvents;
        }
    }

    public static class FilteredEvents {
        @JsonProperty("buy")
        private double buy;

        @JsonProperty("sell")
        private double sell;

        @JsonProperty("overvaluation")
        private double overvaluation;

        @JsonProperty("variation")
        private double variation;

        @JsonProperty("date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime date;

        public double getBuy() {
            return buy;
        }

        public void setBuy(double buy) {
            this.buy = buy;
        }

        public double getSell() {
            return sell;
        }

        public void setSell(double sell) {
            this.sell = sell;
        }

        public double getVariation() {
            return variation;
        }

        public void setVariation(double variation) {
            this.variation = variation;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public double getOvervaluation() {
            return overvaluation;
        }

        public void setOvervaluation(double overvaluation) {
            this.overvaluation = overvaluation;
        }
    }

}
