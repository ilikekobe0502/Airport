package com.whatsmedia.ttia.page.main.terminals.facility.detail;

import android.content.Context;

import com.whatsmedia.ttia.connect.NewApiConnect;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FacilityDetailPresenter implements FacilityDetailContract.Presenter {
    private final static String TAG = FacilityDetailPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private FacilityDetailContract.View mView;


    FacilityDetailPresenter(Context context, FacilityDetailContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
    }
}
