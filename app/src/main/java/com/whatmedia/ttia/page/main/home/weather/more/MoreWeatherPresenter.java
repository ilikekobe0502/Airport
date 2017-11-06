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

    private NewApiConnect mNewApiConnect;
    private MoreWeatherContract.View mView;
    private Context mContext;


    MoreWeatherPresenter(Context context, MoreWeatherContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getWeatherAPI(String cityId) {

        mNewApiConnect.getWeather(cityId, WeatherQueryData.TAG_WEEK_WEATHER, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getApiFailed(mContext.getString(R.string.data_error), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.getApiSucceed(response);
            }
        });
    }
}
