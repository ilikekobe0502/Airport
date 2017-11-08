package com.whatmedia.ttia.page.main.home.weather.more;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetWeatherQueryResponse;
import com.whatmedia.ttia.newresponse.data.WeatherQueryData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MoreWeatherPresenter implements MoreWeatherContract.Presenter {
    private final static String TAG = MoreWeatherPresenter.class.getSimpleName();

    private MoreWeatherContract.View mView;
    private Context mContext;


    MoreWeatherPresenter(Context context, MoreWeatherContract.View view) {
        mView = view;
        mContext = context;
    }

    @Override
    public void getDeviceId() {
        String deviceId = NewApiConnect.getDeviceID();
        if (!TextUtils.isEmpty(deviceId)) {
            mView.getDeviceIdSucceed(deviceId);
        } else {
            mView.getDeviceIdFailed(mContext.getString(R.string.data_error));
        }
    }
}
