package com.whatmedia.ttia.response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetQuestionnaireResponse {
    private static List<QuestionnaireData> data;

    public static List<QuestionnaireData> newInstance(String response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuestionnaireData>>(){}.getType();
        data = gson.fromJson(response, type);
        return data;
    }
}
