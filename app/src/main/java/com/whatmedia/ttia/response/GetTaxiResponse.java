package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.TaxiData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class GetTaxiResponse {
    private static List<TaxiData> data;

    public static List<TaxiData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TaxiData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
