package com.whatmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.FlightsQueryData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetFlightsQueryResponse extends GetBaseResponse {
    private FlightsQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        return data != null ? gson.toJson(data) : "";
    }

    public FlightsQueryData getData() {
        return data;
    }

    public void setData(FlightsQueryData data) {
        this.data = data;
    }
}
