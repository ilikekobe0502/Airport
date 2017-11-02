package com.whatmedia.ttia.newresponse.data;


import com.google.gson.annotations.SerializedName;

public class InternationCallData {
    @SerializedName("content")
    private String icHtml;

    public String getIcHtml() {
        return icHtml;
    }

    public void setIcHtml(String icHtml) {
        this.icHtml = icHtml;
    }
}
