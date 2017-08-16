package com.whatmedia.ttia.response.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class GetStoreCodeResponse {
    private static List<StoreCodeData> data;

    public static List<StoreCodeData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<StoreCodeData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
