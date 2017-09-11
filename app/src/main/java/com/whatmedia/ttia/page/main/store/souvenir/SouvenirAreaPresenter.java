package com.whatmedia.ttia.page.main.store.souvenir;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetSouvenirDataResponse;
import com.whatmedia.ttia.response.data.SouvenirData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SouvenirAreaPresenter implements SouvenirAreaContract.Presenter {
    private final static String TAG = SouvenirAreaPresenter.class.getSimpleName();

    private static SouvenirAreaPresenter mSouvenirAreaPresenter;
    private static ApiConnect mApiConnect;
    private static SouvenirAreaContract.View mView;


    public static SouvenirAreaPresenter getInstance(Context context, SouvenirAreaContract.View view) {
        mSouvenirAreaPresenter = new SouvenirAreaPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mSouvenirAreaPresenter;
    }

    @Override
    public void querySouvenirList() {
        mApiConnect.getSouvenirList(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.querySouvenirListFail(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<SouvenirData> list = GetSouvenirDataResponse.newInstance(result);
                    mView.querySouvenirListSuccess(list);
                } else {
                    mView.querySouvenirListFail(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
