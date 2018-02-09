package com.whatsmedia.ttia.page.main.secretary;

import android.content.Context;

import com.whatsmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportSecretaryPresenter implements AirportSecretaryContract.Presenter {
    private final static String TAG = AirportSecretaryPresenter.class.getSimpleName();

    private static AirportSecretaryPresenter mAirportSecretaryPresenter;
    private static ApiConnect mApiConnect;
    private static AirportSecretaryContract.View mView;


    public static AirportSecretaryPresenter getInstance(Context context, AirportSecretaryContract.View view) {
        mAirportSecretaryPresenter = new AirportSecretaryPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportSecretaryPresenter;
    }
}
