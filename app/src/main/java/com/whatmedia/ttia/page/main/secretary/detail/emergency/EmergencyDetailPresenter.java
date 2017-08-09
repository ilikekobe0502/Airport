package com.whatmedia.ttia.page.main.secretary.detail.emergency;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class EmergencyDetailPresenter implements EmergencyDetailContract.Presenter {
    private final static String TAG = EmergencyDetailPresenter.class.getSimpleName();

    private static EmergencyDetailPresenter mAirportSweetNotifyPresenter;
    private static ApiConnect mApiConnect;
    private static EmergencyDetailContract.View mView;


    public static EmergencyDetailPresenter getInstance(Context context, EmergencyDetailContract.View view) {
        mAirportSweetNotifyPresenter = new EmergencyDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportSweetNotifyPresenter;
    }
}
