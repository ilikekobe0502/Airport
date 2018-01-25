package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class SweetDeleteData {
    @SerializedName("beaconId")
    private List<String> beaconId;

    public List<String> getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(List<String> beaconId) {
        this.beaconId = beaconId;
    }
}
