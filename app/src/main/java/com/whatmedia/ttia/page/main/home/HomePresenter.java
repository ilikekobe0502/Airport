package com.whatmedia.ttia.page.main.home;

import android.content.Context;

import com.whatmedia.ttia.connect.ApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomePresenter {

    private static HomePresenter mHomePresenter;
    private static ApiConnect mApiConnect;
    private static Context mContext;


    public static HomePresenter getInstance(Context context) {
        mContext = context;
        mHomePresenter = new HomePresenter();
        mApiConnect = ApiConnect.getInstance(context);

        return mHomePresenter;
    }
}
