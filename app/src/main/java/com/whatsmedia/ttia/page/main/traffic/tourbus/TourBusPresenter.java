package com.whatsmedia.ttia.page.main.traffic.tourbus;

import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetShuttleBusInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class TourBusPresenter implements TourBusContract.Presenter {
    private final static String TAG = TourBusPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private TourBusContract.View mView;
    private Context mContext;


    TourBusPresenter(Context context, TourBusContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getTourBusAPI() {
        mNewApiConnect.getShuttleInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTourBusFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetShuttleBusInfoResponse shuttleBusInfoResponse = GetShuttleBusInfoResponse.getGson(response);
                if (shuttleBusInfoResponse.getShuttle() != null)
                    mView.getTourBusSucceed(shuttleBusInfoResponse.getShuttle());
                else
                    mView.getTourBusFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
