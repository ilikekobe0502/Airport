package com.whatmedia.ttia.page.main.home.departure;

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

public class DepartureFlightsPresenter implements DepartureFlightsContract.Presenter {
    private final static String TAG = DepartureFlightsPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private DepartureFlightsContract.View mView;
    private Context mContext;


    DepartureFlightsPresenter(Context context, DepartureFlightsContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getDepartureFlightAPI() {
        FlightsQueryData data = new FlightsQueryData();
        GetFlightsQueryResponse flightsListResponse = new GetFlightsQueryResponse();

        data.setQueryType(FlightsQueryData.TAG_DEPARTURE_TOP4);
        data.setExpressDate(Util.getNowDate());
        flightsListResponse.setData(data);
        if (TextUtils.isEmpty(flightsListResponse.getJson())) {
            mView.getDepartureFlightFailed(mContext.getString(R.string.data_error), false);
            return;
        }

        mNewApiConnect.getFlightsListInfo(flightsListResponse.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getDepartureFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetFlightsListResponse flightsListResponse = GetFlightsListResponse.getGson(response);
                List<FlightsListData> flightsListData = flightsListResponse.getFlightList();

                mView.getDepartureFlightSucceed(flightsListData);
            }
        });
    }

    @Override
    public void saveMyFlightsAPI(FlightsListData flightsListData) {
        FlightsListData data = new FlightsListData();
        GetFlightsListResponse response = new GetFlightsListResponse();

        data.setAirlineCode(flightsListData.getAirlineCode());
        data.setShifts(flightsListData.getShifts());
        data.setExpressDate(flightsListData.getExpressDate());
        data.setExpressTime(flightsListData.getExpressTime());

        response.setUploadData(data);
        final String sentJson = response.getJson();

        mNewApiConnect.saveMyFlights(sentJson, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.saveMyFlightFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.saveMyFlightSucceed(response, sentJson);
            }
        });
    }
}
