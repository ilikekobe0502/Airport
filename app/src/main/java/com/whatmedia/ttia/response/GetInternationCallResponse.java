package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.InternationCallData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetInternationCallResponse {
    private static List<InternationCallData> data;

    public static List<InternationCallData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InternationCallData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
