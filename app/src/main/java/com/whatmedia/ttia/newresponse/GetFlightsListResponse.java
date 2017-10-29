package com.whatmedia.ttia.newresponse;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.newresponse.data.FlightsListData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetFlightsListResponse {
    @SerializedName("flightList")
    private FlightsListData flightList;

    public FlightsListData getFlightList() {
        return flightList;
    }

    public void setFlightList(FlightsListData flightList) {
        this.flightList = flightList;
    }
}
