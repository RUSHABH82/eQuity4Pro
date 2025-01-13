package com.equity2profit.datahistoryservice.entity;

import jakarta.persistence.Entity;

public enum CompanyCategory {
    S40, S40NEXT, S250, OTHERS;

    public String getDatabaseValue() {
        return name();
    }

}
