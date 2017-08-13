package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class InternationCallData {
    @SerializedName("ICTitle")
    private String icTitle;
    @SerializedName("ICHTML")
    private String icHtml;

    public String getIcTitle() {
        return icTitle;
    }

    public void setIcTitle(String icTitle) {
        this.icTitle = icTitle;
    }

    public String getIcHtml() {
        return icHtml;
    }

    public void setIcHtml(String icHtml) {
        this.icHtml = icHtml;
    }
}
