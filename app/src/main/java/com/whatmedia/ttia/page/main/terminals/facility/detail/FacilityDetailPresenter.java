package com.whatmedia.ttia.page.main.terminals.facility.detail;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetAirPortFacilityResponse;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FacilityDetailPresenter implements FacilityDetailContract.Presenter {
    private final static String TAG = FacilityDetailPresenter.class.getSimpleName();

    private static FacilityDetailPresenter mAirportFacilityPresenter;
    private static ApiConnect mApiConnect;
    private static FacilityDetailContract.View mView;


    public static FacilityDetailPresenter getInstance(Context context, FacilityDetailContract.View view) {
        mAirportFacilityPresenter = new FacilityDetailPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportFacilityPresenter;
    }
}
