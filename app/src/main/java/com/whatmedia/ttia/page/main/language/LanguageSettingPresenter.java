package com.whatmedia.ttia.page.main.language;


import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

public class LanguageSettingPresenter implements LanguageSettingContract.Presenter {
    private final static String TAG = LanguageSettingPresenter.class.getSimpleName();

    private static LanguageSettingPresenter mUsefulInfoPresenter;
    private static ApiConnect mApiConnect;
    private static LanguageSettingContract.View mView;


    public static LanguageSettingPresenter getInstance(Context context, LanguageSettingContract.View view) {
        mUsefulInfoPresenter = new LanguageSettingPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mUsefulInfoPresenter;
    }
}
