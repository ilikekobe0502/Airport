package com.whatmedia.ttia.response.data;


import com.google.gson.annotations.SerializedName;

public class LostAndFoundData {
    @SerializedName("LostHTML")
    private String lostHtml;
    @SerializedName("lan")
    private String lan;

    public String getLostHtml() {
        return lostHtml;
    }

    public void setLostHtml(String lostHtml) {
        this.lostHtml = lostHtml;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
