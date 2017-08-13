package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.EmergenctCallData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetEmergenctCallResponse {
    private static List<EmergenctCallData> data;

    public static List<EmergenctCallData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<EmergenctCallData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
