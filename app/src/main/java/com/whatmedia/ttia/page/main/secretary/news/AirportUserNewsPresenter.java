package com.whatmedia.ttia.page.main.secretary.news;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetUserNewsResponse;
import com.whatmedia.ttia.response.data.UserNewsData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportUserNewsPresenter implements AirportUserNewsContract.Presenter {
    private final static String TAG = AirportUserNewsPresenter.class.getSimpleName();

    private static AirportUserNewsPresenter mAirportUserNewsPresenter;
    private static ApiConnect mApiConnect;
    private static AirportUserNewsContract.View mView;


    public static AirportUserNewsPresenter getInstance(Context context, AirportUserNewsContract.View view) {
        mAirportUserNewsPresenter = new AirportUserNewsPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportUserNewsPresenter;
    }

    @Override
    public void getUserNewsAPI() {
        mApiConnect.getUserNews(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getUserNewsFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    Log.d(TAG, result);
                    List<UserNewsData> list = GetUserNewsResponse.newInstance(result);
                    mView.getUserNewsSucceed(list);
                } else {
                    mView.getUserNewsFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }
}
