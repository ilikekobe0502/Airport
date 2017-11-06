package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.HomeParkingFrameData;

import java.lang.reflect.Type;

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
