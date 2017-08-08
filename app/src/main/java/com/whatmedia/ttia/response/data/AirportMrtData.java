package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class AirportMrtData {
    @SerializedName("HighTrailHTML")
    private String highTrailHtle;
    @SerializedName("lan")
    private String lan;

    public String getHighTrailHtle() {
        return highTrailHtle;
    }

    public void setHighTrailHtle(String highTrailHtle) {
        this.highTrailHtle = highTrailHtle;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }
}
