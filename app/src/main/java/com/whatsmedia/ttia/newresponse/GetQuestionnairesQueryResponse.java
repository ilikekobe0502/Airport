package com.whatsmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatsmedia.ttia.newresponse.data.AnswerListData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetQuestionnairesQueryResponse extends GetBaseResponse {
    private static final String TAG = GetQuestionnairesQueryResponse.class.getSimpleName();

    private static final String KEY_VALUE = "questionnairesAnswerList";

    private List<AnswerListData> data;

    public String getJson() {
        Map<String,List<AnswerListData>> object = new HashMap<>();
        object.put(KEY_VALUE,data);
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public List<AnswerListData> getData() {
        return data;
    }

    public void setData(List<AnswerListData> data) {
        this.data = data;
    }
}
