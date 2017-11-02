package com.whatmedia.ttia.newresponse;

import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.data.QuestionnairesListData;

import java.util.List;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class GetQuestionnairesQueryResponse extends GetBaseResponse {
    private static final String TAG = GetQuestionnairesQueryResponse.class.getSimpleName();
    private List<QuestionnairesListData> data;

    public String getJson() {
        Gson gson = new Gson();
        String json = data != null ? gson.toJson(data) : "";
        Log.i(TAG, String.format("[Json] %s", json));
        return json;
    }

    public List<QuestionnairesListData> getData() {
        return data;
    }

    public void setData(List<QuestionnairesListData> data) {
        this.data = data;
    }
}
