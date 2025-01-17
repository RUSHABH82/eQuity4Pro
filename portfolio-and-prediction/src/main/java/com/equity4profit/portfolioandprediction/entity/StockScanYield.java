package com.equity4profit.portfolioandprediction.entity;

import com.equity4profit.portfolioandprediction.historydata.CompanyCategory;
import jakarta.persistence.*;

@Entity
public class StockScanYield {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private Double variation;
    private Double buy;
    private Double sell;
    private Long scannedTimeStamp;
    @Enumerated(EnumType.STRING)
    private CompanyCategory companyCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getVariation() {
        return variation;
    }

    public void setVariation(Double variation) {
        this.variation = variation;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Long getScannedTimeStamp() {
        return scannedTimeStamp;
    }

    public void setScannedTimeStamp(Long scannedTimeStamp) {
        this.scannedTimeStamp = scannedTimeStamp;
    }

    public CompanyCategory getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(CompanyCategory companyCategory) {
        this.companyCategory = companyCategory;
    }

    @Override
    public String toString() {
        return "StockScanYield{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", variation=" + variation +
                ", buy=" + buy +
                ", sell=" + sell +
                ", scannedTimeStamp=" + scannedTimeStamp +
                '}';
    }
}