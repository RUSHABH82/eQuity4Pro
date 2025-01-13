package com.equity4profit.liveupdateservice.liveprice.groww;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GrowwModels {


    public static class ExchangeAggReqMap {

        private Map<String, ExchangeInfo> exchangeAggReqMap = Collections.emptyMap();

        public Map<String, ExchangeInfo> getExchangeAggReqMap() {
            return exchangeAggReqMap;
        }

        public void setExchangeAggReqMap(Map<String, ExchangeInfo> exchangeAggReqMap) {
            this.exchangeAggReqMap = exchangeAggReqMap;
        }

        public static class ExchangeInfo {
            private List<String> priceSymbolList = Collections.emptyList();
            private List<String> indexSymbolList = Collections.emptyList();

            public List<String> getPriceSymbolList() {
                return priceSymbolList;
            }

            public void setPriceSymbolList(List<String> priceSymbolList) {
                this.priceSymbolList = priceSymbolList;
            }

            public List<String> getIndexSymbolList() {
                return indexSymbolList;
            }

            public void setIndexSymbolList(List<String> indexSymbolList) {
                this.indexSymbolList = indexSymbolList;
            }
        }
    }

    public static class ExchangeAggResp {

        private String segment;
        private Map<String, ExchangeInfo> exchangeAggRespMap = Collections.emptyMap();

        public String getSegment() {
            return segment;
        }

        public void setSegment(String segment) {
            this.segment = segment;
        }

        public Map<String, ExchangeInfo> getExchangeAggRespMap() {
            return exchangeAggRespMap;
        }

        public void setExchangeAggRespMap(Map<String, ExchangeInfo> exchangeAggRespMap) {
            this.exchangeAggRespMap = exchangeAggRespMap;
        }

        public static class ExchangeInfo {
            private Map<String, GrowPriceLivePoint> priceLivePointsMap = Collections.emptyMap();
            private Map<String, GrowPriceLivePoint> indexLivePointsMap = Collections.emptyMap();

            public Map<String, GrowPriceLivePoint> getPriceLivePointsMap() {
                return priceLivePointsMap;
            }

            public void setPriceLivePointsMap(Map<String, GrowPriceLivePoint> priceLivePointsMap) {
                this.priceLivePointsMap = priceLivePointsMap;
            }

            public Map<String, GrowPriceLivePoint> getIndexLivePointsMap() {
                return indexLivePointsMap;
            }

            public void setIndexLivePointsMap(Map<String, GrowPriceLivePoint> indexLivePointsMap) {
                this.indexLivePointsMap = indexLivePointsMap;
            }
        }
    }

    public static class GrowPriceLivePoint {
        @JsonProperty("symbol")
        String symbol;
        @JsonProperty("ltp")
        double ltp;
    }
}
