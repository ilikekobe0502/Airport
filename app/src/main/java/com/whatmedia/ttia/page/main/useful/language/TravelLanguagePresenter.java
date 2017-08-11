package com.whatmedia.ttia.page.main.useful.language;


import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

public class TravelLanguagePresenter implements TravelLanguageContract.Presenter{
    private final static String TAG = TravelLanguagePresenter.class.getSimpleName();

    private static TravelLanguagePresenter mTravelLanguagePresenter;
    private static ApiConnect mApiConnect;
    private static TravelLanguageContract.View mView;


    public static TravelLanguagePresenter getInstance(Context context, TravelLanguageContract.View view) {
        mTravelLanguagePresenter = new TravelLanguagePresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mTravelLanguagePresenter;
    }
}
