package com.whatmedia.ttia.page.main.communication;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class CommunicationPresenter implements CommunicationContract.Presenter {
    private final static String TAG = CommunicationPresenter.class.getSimpleName();

    private static CommunicationPresenter mFlightsInfoPresenter;
    private static ApiConnect mApiConnect;
    private static CommunicationContract.View mView;


    public static CommunicationPresenter getInstance(Context context, CommunicationContract.View view) {
        mFlightsInfoPresenter = new CommunicationPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mFlightsInfoPresenter;
    }
}
