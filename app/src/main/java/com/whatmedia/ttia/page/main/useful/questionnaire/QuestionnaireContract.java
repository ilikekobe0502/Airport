package com.whatmedia.ttia.page.main.useful.questionnaire;


import com.whatmedia.ttia.newresponse.data.AnswerListData;
import com.whatmedia.ttia.newresponse.data.QuestionnairesListData;

import java.util.List;

public interface QuestionnaireContract {
    interface View {
        void getQuestionnaireSucceed(List<QuestionnairesListData> response);

        void getQuestionnaireFailed(String message, int status);

        void sendQuestionnaireSucceed();

        void sendQuestionnaireFailed(String message, int status);
    }

    interface Presenter {
        void getQuestionnaireAPI();

        void sendQuestionnaireAPI(List<AnswerListData> answer);
    }
}
