package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetExchangeRateResponse extends GetBaseResponse {
    private static final String TAG = GetExchangeRateResponse.class.getSimpleName();
    @SerializedName("amount")
    private float amount;

    public static GetExchangeRateResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetExchangeRateResponse.class);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
