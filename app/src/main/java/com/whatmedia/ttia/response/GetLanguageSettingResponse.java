package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.LanguageData;
import com.whatmedia.ttia.response.data.LanguageSettingData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 2017/8/13.
 */

public class GetLanguageSettingResponse {
    private static List<LanguageSettingData> data;

    public static List<LanguageSettingData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LanguageSettingData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
