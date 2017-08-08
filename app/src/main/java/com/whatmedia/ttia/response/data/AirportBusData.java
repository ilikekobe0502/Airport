package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class AirportBusData {
    @SerializedName("BusesHTML")
    private String busesHtml;
    @SerializedName("lan")
    private String lan;

    public String getBusesHtml() {
        return busesHtml;
    }

    public void setBusesHtml(String busesHtml) {
        this.busesHtml = busesHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
