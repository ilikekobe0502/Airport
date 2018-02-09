package com.whatsmedia.ttia.page.main.traffic.skytrain;

import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetTramInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class SkyTrainPresenter implements SkyTrainContract.Presenter {
    private final static String TAG = SkyTrainPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private SkyTrainContract.View mView;
    private Context mContext;


    SkyTrainPresenter(Context context, SkyTrainContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getSkyTrainAPI() {
        mNewApiConnect.getTramInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getSkyTrainFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTramInfoResponse tramInfoResponse = GetTramInfoResponse.getGson(response);
                if (tramInfoResponse.getTram() != null)
                    mView.getSkyTrainSucceed(tramInfoResponse.getTram());
                else
                    mView.getSkyTrainFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}