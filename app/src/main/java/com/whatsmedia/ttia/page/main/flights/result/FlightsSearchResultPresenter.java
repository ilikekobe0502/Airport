package com.whatsmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.text.TextUtils;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatsmedia.ttia.newresponse.GetFlightsQueryResponse;
import com.whatsmedia.ttia.newresponse.data.FlightsListData;
import com.whatsmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatsmedia.ttia.utility.Util;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FlightsSearchResultPresenter implements FlightsSearchResultContract.Presenter {
    private final static String TAG = FlightsSearchResultPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private FlightsSearchResultContract.View mView;
    private Context mContext;

    private String mQueryDate = Util.getNowDate();
    private String mKeyWorld;
    private int mQueryType = FlightsQueryData.TAG_DEPARTURE_ALL;


    FlightsSearchResultPresenter(Context context, FlightsSearchResultContract.View view) {
        mNewApiConnect = mNewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
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
        final String sentJson = response.getJson();
        if (TextUtils.isEmpty(sentJson)) {
            mView.getFlightFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }

        mNewApiConnect.saveMyFlights(sentJson, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.saveMyFlightFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.saveMyFlightSucceed(response, sentJson);
            }
        });
    }

    @Override
    public void getFlightAPI() {
        FlightsQueryData data = new FlightsQueryData();
        GetFlightsQueryResponse flightsListResponse = new GetFlightsQueryResponse();


        data.setQueryType(mQueryType);
        data.setExpressDate(mQueryDate);
        if (!TextUtils.isEmpty(mKeyWorld)) {
            data.setKeyWord(mKeyWorld);
        } else {
            mKeyWorld = mView.getKeyword();
            if (!TextUtils.isEmpty(mKeyWorld)) {
                data.setKeyWord(mKeyWorld);
            }
        }

        flightsListResponse.setData(data);
        String json = flightsListResponse.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.getFlightFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }

        mNewApiConnect.getFlightsListInfo(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getFlightFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetFlightsListResponse flightsListResponse = GetFlightsListResponse.getGson(response);
                List<FlightsListData> flightsListData = flightsListResponse.getFlightList();

                mView.getFlightSucceed(flightsListData);
            }
        });
    }

    @Override
    public void getFlightByDateAPI(String date) {
        mQueryDate = date;
        getFlightAPI();
    }

    @Override
    public void getFlightByQueryTypeAPI(int queryType) {
        mQueryType = queryType;
        getFlightAPI();
    }
}
