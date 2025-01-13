package com.equity4profit.liveupdateservice.livedetail.tickertape;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class TickerTapeModels {

    static class TickerTapeResponse {
        @JsonProperty("data")
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }


    static class Data {
        @JsonProperty("results")
        private List<Results> results = Collections.emptyList();

        public List<Results> getResults() {
            return results;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }
    }

    static class Results {
        @JsonProperty("sid")
        private String sid;

        @JsonProperty("stock")
        private Stock stock;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Stock getStock() {
            return stock;
        }

        public void setStock(Stock stock) {
            this.stock = stock;
        }
    }

    static class Stock {
        @JsonProperty("advancedRatios")
        private AdvancedRatios advancedRatios;

        @JsonProperty("info")
        private Info info;

        public AdvancedRatios getAdvancedRatios() {
            return advancedRatios;
        }

        public void setAdvancedRatios(AdvancedRatios advancedRatios) {
            this.advancedRatios = advancedRatios;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
    }

    static class AdvancedRatios {
        @JsonProperty("subindustry")
        private String subindustry;

        @JsonProperty("lastPrice")
        private double lastPrice;

        @JsonProperty("mrktCapf")
        private double mrktCapf;

        public String getSubindustry() {
            return subindustry;
        }

        public void setSubindustry(String subindustry) {
            this.subindustry = subindustry;
        }

        public double getMrktCapf() {
            return mrktCapf;
        }

        public void setMrktCapf(double mrktCapf) {
            this.mrktCapf = mrktCapf;
        }

        public double getLastPrice() {
            return lastPrice;
        }

        public void setLastPrice(double lastPrice) {
            this.lastPrice = lastPrice;
        }
    }

    static class Info {

        @JsonProperty("name")
        private String name;
        @JsonProperty("ticker")
        private String ticker;
        @JsonProperty("sector")
        private String sector;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }


    static class TickerTapeRequest {
        @JsonProperty("sortBy")
        private String sortBy = "mrktCapf";
        @JsonProperty("sortOrder")
        private int sortOrder = -1;
        @JsonProperty("project")
        private List<String> project = Collections.emptyList();
        @JsonProperty("offset")
        private int offset = 0;
        @JsonProperty("count")
        private int count = Integer.MAX_VALUE;
        @JsonProperty("sids")
        private List<String> sids = Collections.emptyList();

        private String getSortBy() {
            return sortBy;
        }

        public void setSortBy(String sortBy) {
            this.sortBy = sortBy;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        public List<String> getProject() {
            return project;
        }

        public void setProject(List<String> project) {
            this.project = project;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<String> getSids() {
            return sids;
        }

        public void setSids(List<String> sids) {
            this.sids = sids;
        }
    }
}
