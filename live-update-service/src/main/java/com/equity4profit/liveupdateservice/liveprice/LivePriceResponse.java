package com.equity4profit.liveupdateservice.liveprice;

public class LivePriceResponse {

    private String symbol;
    private Double ltp;

    public LivePriceResponse(String symbol, Double ltp) {
        this.symbol = symbol;
        this.ltp = ltp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLtp() {
        return ltp;
    }

    public void setLtp(Double ltp) {
        this.ltp = ltp;
    }
}
