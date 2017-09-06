package com.whatmedia.ttia.page.main.secretary.sweet;

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

public class AirportSweetNotifyPresenter implements AirportSweetNotifyContract.Presenter {
    private final static String TAG = AirportSweetNotifyPresenter.class.getSimpleName();

    private static AirportSweetNotifyPresenter mAirportSweetNotifyPresenter;
    private static ApiConnect mApiConnect;
    private static AirportSweetNotifyContract.View mView;


    public static AirportSweetNotifyPresenter getInstance(Context context, AirportSweetNotifyContract.View view) {
        mAirportSweetNotifyPresenter = new AirportSweetNotifyPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportSweetNotifyPresenter;
    }

    @Override
    public void getSweetNotifyAPI() {
        mApiConnect.getUserSweetNotify(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getSweetNotifyFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<UserNewsData> list = GetUserNewsResponse.newInstance(result);
                    mView.getSweetNotifySucceed(list);
                } else {
                    mView.getSweetNotifyFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
