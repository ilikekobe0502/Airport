package com.whatmedia.ttia.page.main.traffic.taxi;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetTaxiInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class TaxiPresenter implements TaxiContract.Presenter {
    private final static String TAG = TaxiPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private TaxiContract.View mView;
    private Context mContext;


    TaxiPresenter(Context context, TaxiContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getTaxiAPI() {
        mNewApiConnect.getTaxiInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getTaxiFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTaxiInfoResponse taxiInfoResponse = GetTaxiInfoResponse.getGson(response);
                if (taxiInfoResponse.getTaxi() != null)
                    mView.getTaxiSucceed(taxiInfoResponse.getTaxi());
                else
                    mView.getTaxiFailed(mContext.getString(R.string.data_error), false);

            }
        });
    }
}
