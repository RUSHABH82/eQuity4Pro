package com.equity4profit.portfolioandprediction.historydata;

public enum CompanyCategory {
    S40, S40NEXT, S250, OTHERS;

    public String getDatabaseValue() {
        return name();
    }

}
