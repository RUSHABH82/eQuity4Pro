package com.equity4profit.datahistoryservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "company_details")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetails {

    @JsonProperty("symbol")
    @Id
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

    @Enumerated(EnumType.STRING)
    private CompanyCategory category = CompanyCategory.OTHERS;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyDetails")
    private Set<HistoryData> historyData;

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

    public Set<HistoryData> getHistoryData() {
        return historyData;
    }

    public void setHistoryData(Set<HistoryData> historyData) {
        this.historyData = historyData;
    }

    public CompanyCategory getCategory() {
        return category;
    }

    public void setCategory(CompanyCategory category) {
        this.category = category;
    }
}
