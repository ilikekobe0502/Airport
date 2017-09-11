package com.whatmedia.ttia.page.main.useful.questionnaire;


import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.util.List;

public interface QuestionnaireContract {
    interface View {
        void getQuestionnaireSucceed(List<QuestionnaireData> response);

        void getQuestionnaireFailed(String message, boolean timeout);

        void sendQuestionnaireSucceed(String response);

        void sendQuestionnaireFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getQuestionnaireAPI();
        void sendQuestionnaireAPI(String answer);
    }
}
