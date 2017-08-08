package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class TaxiData {
    @SerializedName("TaxiHTML")
    private String taxiHtml;
    @SerializedName("lan")
    private String lan;

    public String getTaxiHtml() {
        return taxiHtml;
    }

    public void setTaxiHtml(String taxiHtml) {
        this.taxiHtml = taxiHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
