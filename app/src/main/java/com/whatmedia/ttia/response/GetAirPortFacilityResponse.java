package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class GetAirPortFacilityResponse {
    @SerializedName("data")
    private List<AirportFacilityData> data;

    public static List<AirportFacilityData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<AirportFacilityData>() {
        }.getType();
        GetAirPortFacilityResponse airPortFacilityResponse = gson.fromJson(response, GetAirPortFacilityResponse.class);
        if (airPortFacilityResponse != null && airPortFacilityResponse.data != null) {
            return airPortFacilityResponse.data;
        } else
            return new ArrayList<>();
    }
}
