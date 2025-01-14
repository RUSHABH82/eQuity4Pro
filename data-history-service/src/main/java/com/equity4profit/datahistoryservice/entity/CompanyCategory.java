package com.equity4profit.datahistoryservice.entity;

public enum CompanyCategory {
    S40, S40NEXT, S250, OTHERS;

    public String getDatabaseValue() {
        return name();
    }

}
