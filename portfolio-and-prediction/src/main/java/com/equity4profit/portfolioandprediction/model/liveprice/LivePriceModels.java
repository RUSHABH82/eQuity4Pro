package com.equity4profit.portfolioandprediction.model.liveprice;

import java.util.Objects;

public class LivePriceModels {


    public static class LivePriceResponse {

        private String symbol;
        private Double ltp;

        public LivePriceResponse() {
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LivePriceResponse that)) return false;
            return Objects.equals(getSymbol(), that.getSymbol());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSymbol());
        }
    }

}
