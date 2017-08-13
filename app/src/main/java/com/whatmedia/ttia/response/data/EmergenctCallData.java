package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class EmergenctCallData {
    @SerializedName("ECHTML")
    private String ecHtml;

    public String getEcHtml() {
        return ecHtml;
    }

    public void setEcHtml(String ecHtml) {
        this.ecHtml = ecHtml;
    }
}
