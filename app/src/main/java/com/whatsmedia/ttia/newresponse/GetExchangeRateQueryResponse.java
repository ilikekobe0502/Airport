package com.whatsmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.ExchangeRateQueryData;
/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetExchangeRateQueryResponse extends GetBaseResponse {
    private static final String TAG = GetExchangeRateQueryResponse.class.getSimpleName();
    private ExchangeRateQueryData data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public ExchangeRateQueryData getData() {
        return data;
    }

    public void setData(ExchangeRateQueryData data) {
        this.data = data;
    }
}
