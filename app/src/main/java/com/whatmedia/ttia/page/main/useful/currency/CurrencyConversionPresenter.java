package com.whatmedia.ttia.page.main.useful.currency;

import android.content.Context;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.response.GetExangeRateResponse;
import com.whatmedia.ttia.response.data.ExchangeRateData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class CurrencyConversionPresenter implements CurrencyConversionContract.Presenter {
    private final static String TAG = CurrencyConversionPresenter.class.getSimpleName();

    private static CurrencyConversionPresenter mArriveFlightsPresenter;
    private static ApiConnect mApiConnect;
    private static CurrencyConversionContract.View mView;


    public static CurrencyConversionPresenter getInstance(Context context, CurrencyConversionContract.View view) {
        mArriveFlightsPresenter = new CurrencyConversionPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mArriveFlightsPresenter;
    }

    @Override
    public void getExchangeRate(ExchangeRateData data) {
        mApiConnect.getExchangeRate(data, new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getExchangeRateFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, MyResponse response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    ExchangeRateData responseData = GetExangeRateResponse.newInstance(result);
                    mView.getExchangeRateSucceed(responseData);
                } else {
                    Log.e(TAG, "getExchangeRate code = " + response.code() + " message = " + response.message());
                    mView.getExchangeRateFailed(response.message(), false);
                }
            }
        });
    }
}
