package com.whatmedia.ttia.page.main.traffic.tourbus;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetTourBusResponse;
import com.whatmedia.ttia.response.data.TourBusData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class TourBusPresenter implements TourBusContract.Presenter {
    private final static String TAG = TourBusPresenter.class.getSimpleName();

    private static TourBusPresenter mTourBusPresenter;
    private static ApiConnect mApiConnect;
    private static TourBusContract.View mView;


    public static TourBusPresenter getInstance(Context context, TourBusContract.View view) {
        mTourBusPresenter = new TourBusPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mTourBusPresenter;
    }

    @Override
    public void getTourBusAPI() {
        mApiConnect.getTourBus(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTourBusFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<TourBusData> list = GetTourBusResponse.newInstance(result);
                    mView.getTourBusSucceed(list);
                } else {
                    mView.getTourBusFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });

    }
}
