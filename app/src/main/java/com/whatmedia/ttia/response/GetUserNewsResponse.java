package com.whatmedia.ttia.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.UserNewsData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class GetUserNewsResponse {
    private static List<UserNewsData> data;

    public static List<UserNewsData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<UserNewsData>>() {
        }.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
