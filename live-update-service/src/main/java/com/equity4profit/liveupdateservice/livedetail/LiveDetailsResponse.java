package com.equity4profit.liveupdateservice.livedetail;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveDetailsResponse {

    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sector")
    private String sector;
    @JsonProperty("subindustry")
    private String industry;
    @JsonProperty("lastPrice")
    private Double lastPrice;
    @JsonProperty("mrktCapf")
    private Double marketCapInCr;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getMarketCapInCr() {
        return marketCapInCr;
    }

    public void setMarketCapInCr(Double marketCapInCr) {
        this.marketCapInCr = marketCapInCr;
    }
}
