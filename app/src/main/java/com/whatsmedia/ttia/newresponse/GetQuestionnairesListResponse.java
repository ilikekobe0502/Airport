package com.whatsmedia.ttia.newresponse;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.whatsmedia.ttia.newresponse.data.QuestionnairesListData;

import java.util.List;

/**
 * Created by neo on 2017/10/31.
 */

public class GetQuestionnairesListResponse {
    @SerializedName("questionnairesList")
    private List<QuestionnairesListData> questionnairesList;

    public static GetQuestionnairesListResponse getGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetQuestionnairesListResponse.class);
    }

    public List<QuestionnairesListData> getQuestionnairesList() {
        return questionnairesList;
    }

    public void setQuestionnairesList(List<QuestionnairesListData> questionnairesList) {
        this.questionnairesList = questionnairesList;
    }
}
