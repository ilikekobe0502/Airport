package com.whatmedia.ttia.page.main.secretary.detail.sweet;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class SweetNotifyDetailPresenter implements SweetNotifyDetailContract.Presenter {
    private final static String TAG = SweetNotifyDetailPresenter.class.getSimpleName();

    private static SweetNotifyDetailPresenter mAirportSweetNotifyPresenter;
    private static ApiConnect mApiConnect;
    private static SweetNotifyDetailContract.View mView;


    public static SweetNotifyDetailPresenter getInstance(Context context, SweetNotifyDetailContract.View view) {
        mAirportSweetNotifyPresenter = new SweetNotifyDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportSweetNotifyPresenter;
    }
}
