package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.LanguageData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetLanguageResponse {
    private static List<LanguageData> data;

    public static List<LanguageData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LanguageData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
