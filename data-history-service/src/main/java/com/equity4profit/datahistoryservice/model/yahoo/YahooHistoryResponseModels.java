package com.equity4profit.datahistoryservice.model.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YahooHistoryResponseModels {


    public static class YahooHistoryResponse {
        @JsonProperty("chart")
        private Chart chart;

        public Chart getChart() {
            return chart;
        }

        public void setChart(Chart chart) {
            this.chart = chart;
        }
    }

    public static class Chart {
        @JsonProperty("result")
        private List<Result> result;
        @JsonProperty("error")
        private Error error;

        public List<Result> getResult() {
            return result;
        }

        public void setResult(List<Result> result) {
            this.result = result;
        }

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }
    }

    public static class Result {
        @JsonProperty("meta")
        private Meta meta;
        @JsonProperty("timestamp")
        private List<Long> timestamp;
        @JsonProperty("indicators")
        private Indicators indicators;

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public List<Long> getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(List<Long> timestamp) {
            this.timestamp = timestamp;
        }

        public Indicators getIndicators() {
            return indicators;
        }

        public void setIndicators(Indicators indicators) {
            this.indicators = indicators;
        }
    }

    public static class Meta {
        @JsonProperty("symbol")
        String symbol;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }

    public static class Indicators {
        @JsonProperty("quote")
        private List<Quote> quote;

        public List<Quote> getQuote() {
            return quote;
        }

        public void setQuote(List<Quote> quote) {
            this.quote = quote;
        }
    }

    public static class Quote {
        @JsonProperty("high")
        private List<Double> high;
        @JsonProperty("volume")
        private List<Long> volume;
        @JsonProperty("low")
        private List<Double> low;
        @JsonProperty("close")
        private List<Double> close;
        @JsonProperty("open")
        private List<Double> open;

        public List<Double> getHigh() {
            return high;
        }

        public void setHigh(List<Double> high) {
            this.high = high;
        }

        public List<Long> getVolume() {
            return volume;
        }

        public void setVolume(List<Long> volume) {
            this.volume = volume;
        }

        public List<Double> getLow() {
            return low;
        }

        public void setLow(List<Double> low) {
            this.low = low;
        }

        public List<Double> getClose() {
            return close;
        }

        public void setClose(List<Double> close) {
            this.close = close;
        }

        public List<Double> getOpen() {
            return open;
        }

        public void setOpen(List<Double> open) {
            this.open = open;
        }
    }

    public static class Error {
        @JsonProperty("code")
        private String code;
        @JsonProperty("description")
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
