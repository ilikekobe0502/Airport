package com.whatmedia.ttia.response.data;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetLostAndFoundResponse {
    private static List<LostAndFoundData> data;

    public static List<LostAndFoundData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LostAndFoundData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
