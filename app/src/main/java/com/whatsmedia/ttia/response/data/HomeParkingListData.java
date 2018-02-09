package com.whatsmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neo_mac on 2017/8/7.
 */

public class HomeParkingListData {
    @SerializedName("PARK")
    private List<HomeParkingInfoData> park;

    public List<HomeParkingInfoData> getPark() {
        return park;
    }

    public void setPark(List<HomeParkingInfoData> park) {
        this.park = park;
    }
}
