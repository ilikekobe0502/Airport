package com.whatmedia.ttia.page.main.useful.timezone;


import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

public class TimeZoneQueryPresenter implements TimeZoneQueryContract.Presenter{
    private final static String TAG = TimeZoneQueryPresenter.class.getSimpleName();

    private static TimeZoneQueryPresenter mTimeZoneQueryPresenter;
    private static ApiConnect mApiConnect;
    private static TimeZoneQueryContract.View mView;


    public static TimeZoneQueryPresenter getInstance(Context context, TimeZoneQueryContract.View view) {
        mTimeZoneQueryPresenter = new TimeZoneQueryPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mTimeZoneQueryPresenter;
    }
}
