package com.whatmedia.ttia.page.main.traffic.bus;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetBusInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportBusPresenter implements AirportBusContract.Presenter {
    private final static String TAG = AirportBusPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private AirportBusContract.View mView;
    private Context mContext;


    AirportBusPresenter(Context context, AirportBusContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getAirportBusAPI() {
        mNewApiConnect.getBusInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getAirportBusFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetBusInfoResponse bustInfoResponse = GetBusInfoResponse.getGson(response);
                if (bustInfoResponse.getBus() != null) {
                    mView.getAirportBusSucceed(bustInfoResponse.getBus());
                } else {
                    mView.getAirportBusFailed(mContext.getString(R.string.data_error), false);
                }
            }
        });
    }
}
