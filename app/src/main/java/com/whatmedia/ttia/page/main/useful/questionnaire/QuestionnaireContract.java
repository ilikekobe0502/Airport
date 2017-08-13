package com.whatmedia.ttia.page.main.useful.questionnaire;


import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.util.List;

public interface QuestionnaireContract {
    interface View {
        void getQuestionnaireSucceed(List<QuestionnaireData> response);

        void getQuestionnaireFailed(String message);

        void sendQuestionnaireSucceed(String response);

        void sendQuestionnaireFailed(String message);
    }

    interface Presenter {
        void getQuestionnaireAPI();
        void sendQuestionnaireAPI(String answer);
    }
}