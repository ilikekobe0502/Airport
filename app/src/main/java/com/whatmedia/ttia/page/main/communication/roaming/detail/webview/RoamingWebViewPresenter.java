package com.whatmedia.ttia.page.main.communication.roaming.detail.webview;


import android.content.Context;

import com.whatmedia.ttia.connect.NewApiConnect;

public class RoamingWebViewPresenter implements RoamingWebViewContract.Presenter {
    private final static String TAG = RoamingWebViewPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private RoamingWebViewContract.View mView;
    private Context mContext;

    RoamingWebViewPresenter(Context context, RoamingWebViewContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }
}
