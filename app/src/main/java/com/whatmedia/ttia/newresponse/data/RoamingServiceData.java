package com.whatmedia.ttia.newresponse.data;


import com.google.gson.annotations.SerializedName;

public class RoamingServiceData {
    @SerializedName("id")
    private String tiId;
    @SerializedName("name")
    private String tiName;

    public String getTiId() {
        return tiId;
    }

    public void setTiId(String tiId) {
        this.tiId = tiId;
    }

    public String getTiName() {
        return tiName;
    }

    public void setTiName(String tiName) {
        this.tiName = tiName;
    }
}
