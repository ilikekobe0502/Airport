package com.whatmedia.ttia.page.main.useful.lost;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetLostAndFoundResponse;
import com.whatmedia.ttia.response.data.LostAndFoundData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LostAndFoundPresenter implements LostAndFoundContract.Presenter{
    private final static String TAG = LostAndFoundPresenter.class.getSimpleName();

    private static LostAndFoundPresenter mLostAndFoundPresenter;
    private static ApiConnect mApiConnect;
    private static LostAndFoundContract.View mView;


    public static LostAndFoundPresenter getInstance(Context context, LostAndFoundContract.View view) {
        mLostAndFoundPresenter = new LostAndFoundPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mLostAndFoundPresenter;
    }

    @Override
    public void getLostAndFoundAPI() {
        mApiConnect.getLostAndFound(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getLostAndFoundFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<LostAndFoundData> list = GetLostAndFoundResponse.newInstance(result);
                    mView.getLostAndFoundSucceed(list);
                } else {
                    mView.getLostAndFoundFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }
}
