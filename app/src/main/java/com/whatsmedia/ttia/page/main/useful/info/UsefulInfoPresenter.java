package com.whatsmedia.ttia.page.main.useful.info;


import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

public class UsefulInfoPresenter implements UsefulInfoContract.Presenter {
    private final static String TAG = UsefulInfoPresenter.class.getSimpleName();

    private static UsefulInfoPresenter mUsefulInfoPresenter;
    private static ApiConnect mApiConnect;
    private static UsefulInfoContract.View mView;


    public static UsefulInfoPresenter getInstance(Context context, UsefulInfoContract.View view) {
        mUsefulInfoPresenter = new UsefulInfoPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mUsefulInfoPresenter;
    }
}
