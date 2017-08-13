package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.RoamingServiceData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetRoamingServiceResponse {
    private static List<RoamingServiceData> data;

    public static List<RoamingServiceData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RoamingServiceData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
