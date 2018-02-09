package com.whatsmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/8/7.
 */

public class HomeParkingFrameData {
    @SerializedName("ParkAvailable")
    private HomeParkingListData parkingListData;
    @SerializedName("ParkLotInfo")
    private HomeParkingListData parkLotInfo;

    public HomeParkingListData getParkingListData() {
        return parkingListData;
    }

    public void setParkingListData(HomeParkingListData parkingListData) {
        this.parkingListData = parkingListData;
    }

    public HomeParkingListData getParkLotInfo() {
        return parkLotInfo;
    }

    public void setParkLotInfo(HomeParkingListData parkLotInfo) {
        this.parkLotInfo = parkLotInfo;
    }
}
