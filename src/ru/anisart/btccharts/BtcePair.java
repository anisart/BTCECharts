package ru.anisart.btccharts;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcePair {

    @JsonProperty("high")
    private double high;

    @JsonProperty("low")
    private double low;

    @JsonProperty("avg")
    private double avg;

    @JsonProperty("vol")
    private double vol;

    @JsonProperty("vol_cur")
    private double vol_cur;

    @JsonProperty("last")
    private double last;

    @JsonProperty("buy")
    private double buy;

    @JsonProperty("sell")
    private double sell;

    @JsonProperty("updated")
    private int updated;

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getVol_cur() {
        return vol_cur;
    }

    public void setVol_cur(double vol_cur) {
        this.vol_cur = vol_cur;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "BtcePair{" +
                "high=" + high +
                ", low=" + low +
                ", avg=" + avg +
                ", vol=" + vol +
                ", vol_cur=" + vol_cur +
                ", last=" + last +
                ", buy=" + buy +
                ", sell=" + sell +
                ", updated=" + updated +
                '}';
    }
}
