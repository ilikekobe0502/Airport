package com.whatmedia.ttia.page.main.home.arrive;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.GetFlightsQueryResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;
import com.whatmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.util.List;

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
    public void getArriveFlightAPI() {
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
                mView.getArriveFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetFlightsListResponse flightsListResponse = GetFlightsListResponse.getGson(response);
                List<FlightsListData> flightsListData = flightsListResponse.getFlightList();

                mView.getArriveFlightSucceed(flightsListData);
            }
        });
    }

    @Override
    public void saveMyFlightsAPI(FlightsListData flightsListData) {
        FlightsListData data = new FlightsListData();
        GetFlightsListResponse response = new GetFlightsListResponse();

        data.setAirlineCode(!TextUtils.isEmpty(flightsListData.getAirlineCode()) ? flightsListData.getAirlineCode() : "");
        data.setShifts(!TextUtils.isEmpty(flightsListData.getShifts()) ? flightsListData.getShifts() : "");
        data.setExpressDate(!TextUtils.isEmpty(flightsListData.getExpressDate()) ? flightsListData.getExpressDate() : "");
        data.setExpressTime(!TextUtils.isEmpty(flightsListData.getExpressTime()) ? flightsListData.getExpressTime() : "");

        response.setUploadData(data);

        mNewApiConnect.saveMyFlights(response.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.saveMyFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.saveMyFlightSucceed(response);
            }
        });
    }
}
