package com.equity4profit.portfolioandprediction.historydata;

public class HistoryData implements Comparable<HistoryData> {

    private Long timestamp;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;

    public HistoryData() {
        super();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public HistoryData(Long timestamp, Double open, Double close, Double high, Double low, Long volume) {
        this.timestamp = timestamp;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Override
    public int compareTo(HistoryData o) {
        return Long.compare(timestamp, o.timestamp);
    }
}
