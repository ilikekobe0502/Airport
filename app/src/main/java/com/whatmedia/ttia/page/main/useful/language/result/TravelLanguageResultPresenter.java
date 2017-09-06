package com.whatmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetLanguageResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class TravelLanguageResultPresenter implements TravelLanguageResultContract.Presenter {
    private final static String TAG = TravelLanguageResultPresenter.class.getSimpleName();

    private static TravelLanguageResultPresenter mTravelLanguageResultPresenter;
    private static ApiConnect mApiConnect;
    private static TravelLanguageResultContract.View mView;


    public static TravelLanguageResultPresenter getInstance(Context context, TravelLanguageResultContract.View view) {
        mTravelLanguageResultPresenter = new TravelLanguageResultPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mTravelLanguageResultPresenter;
    }

    @Override
    public void getLanguageAPI(int id) {
        mApiConnect.getLanguages(id + "", new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getLanguageFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    mView.getLanguageSucceed(GetLanguageResponse.newInstance(result));
                } else {
                    mView.getLanguageFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
