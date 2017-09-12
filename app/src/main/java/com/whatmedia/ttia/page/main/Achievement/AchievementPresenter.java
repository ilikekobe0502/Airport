package com.whatmedia.ttia.page.main.Achievement;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetAchievementsDataResponse;
import com.whatmedia.ttia.response.data.AchievementsData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class AchievementPresenter implements AchievementContract.Presenter {
    private final static String TAG = AchievementPresenter.class.getSimpleName();

    private static AchievementPresenter mAchievementPresenter;
    private static ApiConnect mApiConnect;
    private static AchievementContract.View mView;


    public static AchievementPresenter getInstance(Context context, AchievementContract.View view) {
        mAchievementPresenter = new AchievementPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAchievementPresenter;
    }

    @Override
    public boolean queryAchievementList() {
        return mApiConnect.getAchievementList(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.queryAchievementListFail(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AchievementsData> list = GetAchievementsDataResponse.newInstance(result);
                    mView.queryAchievementListSuccess(list);
                } else {
                    mView.queryAchievementListFail(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });

    }
}
