package com.whatmedia.ttia.page.main.secretary.emergency;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetUserNewsResponse;
import com.whatmedia.ttia.response.data.UserNewsData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportEmergencyPresenter implements AirportEmergencyContract.Presenter {
    private final static String TAG = AirportEmergencyPresenter.class.getSimpleName();

    private static AirportEmergencyPresenter mAirportEmergencyPresenter;
    private static ApiConnect mApiConnect;
    private static AirportEmergencyContract.View mView;


    public static AirportEmergencyPresenter getInstance(Context context, AirportEmergencyContract.View view) {
        mAirportEmergencyPresenter = new AirportEmergencyPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportEmergencyPresenter;
    }

    @Override
    public void getEmergencyAPI() {
        mApiConnect.getUserEmergency(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getEmergencyFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<UserNewsData> list = GetUserNewsResponse.newInstance(result);
                    mView.getEmergencySucceed(list);
                } else
                    mView.getEmergencyFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
            }
        });
    }
}
