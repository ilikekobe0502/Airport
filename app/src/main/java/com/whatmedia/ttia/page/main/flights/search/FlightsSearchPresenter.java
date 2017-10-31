package com.whatmedia.ttia.page.main.flights.search;

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

public class FlightsSearchPresenter implements FlightsSearchContract.Presenter {
    private final static String TAG = FlightsSearchPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private FlightsSearchContract.View mView;
    private Context mContext;


    FlightsSearchPresenter(Context context, FlightsSearchContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getFlightsInfoAPI(final String keyword) {
        FlightsQueryData data = new FlightsQueryData();
        GetFlightsQueryResponse flightsListResponse = new GetFlightsQueryResponse();


        data.setQueryType(FlightsQueryData.TAG_DEPARTURE_ALL);
        data.setExpressDate(Util.getNowDate());
        data.setKeyWord(keyword);
        flightsListResponse.setData(data);
        if (TextUtils.isEmpty(flightsListResponse.getJson())) {
            mView.getFlightsDepartureFailed(mContext.getString(R.string.data_error), false);
            return;
        }

        mNewApiConnect.getFlightsListInfo(flightsListResponse.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getFlightsDepartureFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.getFlightsDepartureSucceed(response);
            }
        });
    }
}
