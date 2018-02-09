package com.whatsmedia.ttia.page.main.Achievement.Detail;


import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

public class AchievementDetailPresenter implements AchievementDetailContract.Presenter{
    private final static String TAG = AchievementDetailPresenter.class.getSimpleName();

    private static AchievementDetailPresenter mAchievementDetailPresenter;
    private static ApiConnect mApiConnect;
    private static AchievementDetailContract.View mView;


    public static AchievementDetailPresenter getInstance(Context context, AchievementDetailContract.View view) {
        mAchievementDetailPresenter = new AchievementDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAchievementDetailPresenter;
    }
}
