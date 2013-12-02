package ru.anisart.btccharts;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BtceTicker {

    @JsonProperty("btc_usd")
    BtcePair pair;

    public BtcePair getPair() {
        return pair;
    }

    public void setPair(BtcePair pair) {
        this.pair = pair;
    }

    @Override
    public String toString() {
        return "BtceTicker{" + pair + '}';
    }
}