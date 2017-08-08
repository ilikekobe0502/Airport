package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.AirportBusData;
import com.whatmedia.ttia.response.data.HomeParkingFrameData;
import com.whatmedia.ttia.response.data.HomeParkingInfoData;
import com.whatmedia.ttia.response.data.HomeParkingListData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class GetHomeParkingInfoListResponse {
    private static HomeParkingFrameData data;

    public static HomeParkingFrameData newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<HomeParkingFrameData>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
