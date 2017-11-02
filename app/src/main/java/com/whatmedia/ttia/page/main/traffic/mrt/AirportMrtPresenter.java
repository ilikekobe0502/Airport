package com.whatmedia.ttia.page.main.traffic.mrt;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetMrtInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportMrtPresenter implements AirportMrtContract.Presenter {
    private final static String TAG = AirportMrtPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private AirportMrtContract.View mView;
    private Context mContext;


    AirportMrtPresenter(Context context, AirportMrtContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getAirportMrtAPI() {
        mNewApiConnect.getMrtHsrInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getAirportMrtFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetMrtInfoResponse mrtInfoResponse = GetMrtInfoResponse.getGson(response);
                if (mrtInfoResponse.getMrtHsr() != null)
                    mView.getAirportMrtSucceed(mrtInfoResponse.getMrtHsr());
                else
                    mView.getAirportMrtFailed(mContext.getString(R.string.data_error), false);
            }
        });
    }
}
