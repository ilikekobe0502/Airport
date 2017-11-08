package com.whatmedia.ttia.page.main.home.weather;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetWeatherQueryResponse;
import com.whatmedia.ttia.newresponse.data.WeatherQueryData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomeWeatherInfoPresenter implements HomeWeatherInfoContract.Presenter {
    private final static String TAG = HomeWeatherInfoPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private HomeWeatherInfoContract.View mView;
    private Context mContext;


    HomeWeatherInfoPresenter(Context context, HomeWeatherInfoContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }
}
