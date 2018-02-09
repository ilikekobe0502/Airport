package com.whatsmedia.ttia.page.main.store.souvenir.detail;


import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

public class SouvenirDetailPresenter implements SouvenirDetailContract.Presenter{
    private final static String TAG = SouvenirDetailPresenter.class.getSimpleName();

    private static SouvenirDetailPresenter mSouvenirDetailPresenter;
    private static ApiConnect mApiConnect;
    private static SouvenirDetailContract.View mView;


    public static SouvenirDetailPresenter getInstance(Context context, SouvenirDetailContract.View view) {
        mSouvenirDetailPresenter = new SouvenirDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mSouvenirDetailPresenter;
    }
}
