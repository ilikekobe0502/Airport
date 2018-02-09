package com.whatsmedia.ttia.page.main.useful.questionnaire;


import android.content.Context;
import android.text.TextUtils;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetQuestionnairesListResponse;
import com.whatsmedia.ttia.newresponse.GetQuestionnairesQueryResponse;
import com.whatsmedia.ttia.newresponse.data.AnswerListData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class QuestionnairePresenter implements QuestionnaireContract.Presenter {
    private final static String TAG = QuestionnairePresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private QuestionnaireContract.View mView;
    private Context mContext;


    QuestionnairePresenter(Context context, QuestionnaireContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getQuestionnaireAPI() {
        mNewApiConnect.getQuestionnairesList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getQuestionnaireFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetQuestionnairesListResponse questionnairesListResponse = GetQuestionnairesListResponse.getGson(response);
                if (questionnairesListResponse.getQuestionnairesList() != null)
                    mView.getQuestionnaireSucceed(questionnairesListResponse.getQuestionnairesList());
                else
                    mView.getQuestionnaireFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void sendQuestionnaireAPI(List<AnswerListData> answer) {

        GetQuestionnairesQueryResponse questionnairesQueryResponse = new GetQuestionnairesQueryResponse();
        questionnairesQueryResponse.setData(answer);
        String json = questionnairesQueryResponse.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.sendQuestionnaireFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }
        mNewApiConnect.sentQuestioonaires(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.sendQuestionnaireFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.sendQuestionnaireSucceed();
            }
        });
    }


}
