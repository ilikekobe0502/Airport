package com.whatmedia.ttia.page.main.home.arrive;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetFlightsQueryResponse;
import com.whatmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class ArriveFlightsPresenter implements ArriveFlightsContract.Presenter {
    private final static String TAG = ArriveFlightsPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private ArriveFlightsContract.View mView;
    private Context mContext;


    ArriveFlightsPresenter(Context context, ArriveFlightsContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
    }

    @Override
    public void getArriveFlightAPI(FlightSearchData searchData) {
        FlightsQueryData data = new FlightsQueryData();
        GetFlightsQueryResponse flightsListResponse = new GetFlightsQueryResponse();


        data.setQueryType(FlightsQueryData.TAG_ARRIVE_TOP4);
        data.setExpressDate(Util.getNowDate());
        flightsListResponse.setData(data);
        if (TextUtils.isEmpty(flightsListResponse.getJson())) {
            mView.getArriveFlightFailed(mContext.getString(R.string.data_error), false);
            return;
        }

        mNewApiConnect.getFlightsListInfo(flightsListResponse.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                // TODO: 2017/10/29 Failure flow
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                // TODO: 2017/10/29 Success flow
            }
        });
    }

    @Override
    public void saveMyFlightsAPI(FlightsInfoData data) {
//        mApiConnect.doMyFlights(data, new ApiConnect.MyCallback() {
//            @Override
//            public void onFailure(Call call, IOException e, boolean timeout) {
//                mView.saveMyFlightFailed(e.toString(), timeout);
//            }
//
//            @Override
//            public void onResponse(Call call, MyResponse response) throws IOException {
//                if (response.code() == 200) {
//                    String result = response.body().string();
//                    Log.d(TAG, result);
//                    mView.saveMyFlightSucceed(result);
//                } else {
//                    mView.saveMyFlightFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
//                }
//            }
//        });
    }
}
