package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.ExchangeRateData;

import java.lang.reflect.Type;

/**
 * Created by neo_mac on 2017/8/11.
 */

public class GetExangeRateResponse {

    private static ExchangeRateData data;

    public static ExchangeRateData newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ExchangeRateData>() {}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
