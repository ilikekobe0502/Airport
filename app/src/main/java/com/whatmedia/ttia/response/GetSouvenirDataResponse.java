package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.SouvenirData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetSouvenirDataResponse {
    @SerializedName("data")
    private List<SouvenirData> data;

    public static List<SouvenirData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SouvenirData>>(){}.getType();
        GetSouvenirDataResponse souvenirDataResponse = gson.fromJson(response, GetSouvenirDataResponse.class);
        if (souvenirDataResponse != null && souvenirDataResponse.data != null) {
            return souvenirDataResponse.data;
        } else
            return new ArrayList<>();
    }
}
