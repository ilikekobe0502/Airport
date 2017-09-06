package com.whatmedia.ttia.page.main.useful.questionnaire;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetQuestionnaireResponse;
import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class QuestionnairePresenter implements QuestionnaireContract.Presenter {
    private final static String TAG = QuestionnairePresenter.class.getSimpleName();

    private static QuestionnairePresenter mQuestionnairePresenter;
    private static ApiConnect mApiConnect;
    private static QuestionnaireContract.View mView;


    public static QuestionnairePresenter getInstance(Context context, QuestionnaireContract.View view) {
        mQuestionnairePresenter = new QuestionnairePresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mQuestionnairePresenter;
    }

    @Override
    public void getQuestionnaireAPI() {
        mApiConnect.getQuestionnaire(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getQuestionnaireFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<QuestionnaireData> list = GetQuestionnaireResponse.newInstance(result);
                    mView.getQuestionnaireSucceed(list);
                } else {
                    mView.getQuestionnaireFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }

    @Override
    public void sendQuestionnaireAPI(String answer) {
        mApiConnect.sendQuestionnaireResult(answer, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.sendQuestionnaireFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    mView.sendQuestionnaireSucceed(result);
                } else {
                    mView.sendQuestionnaireFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }


}
