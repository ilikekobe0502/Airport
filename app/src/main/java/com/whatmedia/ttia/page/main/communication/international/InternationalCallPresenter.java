package com.whatmedia.ttia.page.main.communication.international;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetInternationCallResponse;
import com.whatmedia.ttia.response.data.InternationCallData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class InternationalCallPresenter implements InternationalCallContract.Presenter {
    private final static String TAG = InternationalCallPresenter.class.getSimpleName();

    private static InternationalCallPresenter mInternationalCallPresenter;
    private static ApiConnect mApiConnect;
    private static InternationalCallContract.View mView;


    public static InternationalCallPresenter getInstance(Context context, InternationalCallContract.View view) {
        mInternationalCallPresenter = new InternationalCallPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mInternationalCallPresenter;
    }

    @Override
    public void getInternationalCallAPI() {
        mApiConnect.getInternationCall(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getInternationalCallFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<InternationCallData> list = GetInternationCallResponse.newInstance(result);
                    mView.getInternationalCallSucceed(list);
                } else {
                    mView.getInternationalCallFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
