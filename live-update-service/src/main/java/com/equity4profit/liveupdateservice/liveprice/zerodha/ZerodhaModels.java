package com.equity4profit.liveupdateservice.liveprice.zerodha;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class ZerodhaModels {


    public static class ZerodhaConstituentsResponse {
        @JsonProperty("data")
        private ConstituentsData data;

        public ConstituentsData getData() {
            return data;
        }

        public void setData(ConstituentsData data) {
            this.data = data;
        }
    }


    public static class ConstituentsData {
        @JsonProperty("constituents")
        private List<Constituents> constituents = Collections.emptyList();

        public List<Constituents> getConstituents() {
            return constituents;
        }

        public void setConstituents(List<Constituents> constituents) {
            this.constituents = constituents;
        }
    }

    public static class Constituents {

        @JsonProperty("sid")
        String sid;

        @JsonProperty("lastPrice")
        double lastPrice;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public double getLastPrice() {
            return lastPrice;
        }

        public void setLastPrice(double lastPrice) {
            this.lastPrice = lastPrice;
        }
    }


    public static class ZerodhaQuoteApiResponse {
        @JsonProperty("data")
        private List<Data> data = Collections.emptyList();

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }

    public static class Data {
        @JsonProperty("sid")
        private String sid;
        @JsonProperty("price")
        private Double price;
        @JsonProperty("vol")
        private Long vol;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Long getVol() {
            return vol;
        }

        public void setVol(Long vol) {
            this.vol = vol;
        }
    }
}
