package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.SouvenirData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetSouvenirDataResponse {
    private static List<SouvenirData> data;

    public static List<SouvenirData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SouvenirData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
